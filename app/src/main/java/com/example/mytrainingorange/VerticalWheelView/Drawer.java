package com.example.mytrainingorange.VerticalWheelView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.Arrays;
import static java.lang.Math.PI;
import static java.lang.Math.sin;

class Drawer {

    private static final int DP_CURSOR_CORNERS_RADIUS = 1;
    private static final int DP_NORMAL_MARK_HEIGHT = 1;
    private static final int DP_ZERO_MARK_HEIGHT = 2;
    private static final int DP_CURSOR_HEIGHT = 3;
    private static final float NORMAL_MARK_RELATIVE_WIDTH = 0.6f;
    private static final float ZERO_MARK_RELATIVE_WIDTH = 0.8f;
    private static final float CURSOR_RELATIVE_WIDTH = 1f;
    private static final float SHADE_RANGE = 0.7f;
    private static final float SCALE_RANGE = 0.1f;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private VerticalWheelView view;
    private int marksCount;
    private int normalColor;
    private int activeColor;
    private boolean showActiveRange;
    private float[] gaps;
    private float[] shades;
    private float[] scales;
    private int[] colorSwitches = {-1, -1, -1};
    private int viewportWidth;
    private int normalMarkWidth;
    private int normalMarkHeight;
    private int zeroMarkWidth;
    private int zeroMarkHeight;
    private int cursorCornersRadius;
    private RectF cursorRect = new RectF();
    private int maxVisibleMarksCount;

    Drawer(VerticalWheelView view) {
        this.view = view;
        initDpSizes();
    }

    private void initDpSizes() {
        normalMarkHeight = convertToPx(DP_NORMAL_MARK_HEIGHT);
        zeroMarkHeight = convertToPx(DP_ZERO_MARK_HEIGHT);
        cursorCornersRadius = convertToPx(DP_CURSOR_CORNERS_RADIUS);
    }

    private int convertToPx(int dp) {
        return Utils.convertToPx(dp, view.getResources());
    }

    void setMarksCount(int marksCount) {
        this.marksCount = marksCount;
        maxVisibleMarksCount = (marksCount / 2) + 1;
        gaps = new float[maxVisibleMarksCount];
        shades = new float[maxVisibleMarksCount];
        scales = new float[maxVisibleMarksCount];
    }

    void setNormalColor(int color) {
        normalColor = color;
    }

    void setActiveColor(int color) {
        activeColor = color;
    }

    void setShowActiveRange(boolean show) {
        showActiveRange = show;
    }

    void onSizeChanged() {
        viewportWidth = view.getWidth() - view.getPaddingLeft() - view.getPaddingRight();
        normalMarkWidth = (int) (viewportWidth * NORMAL_MARK_RELATIVE_WIDTH);
        zeroMarkWidth = (int) (viewportWidth * ZERO_MARK_RELATIVE_WIDTH);
        setupCursorRect();
    }

    private void setupCursorRect() {
        int cursorWidth = (int) (viewportWidth * CURSOR_RELATIVE_WIDTH);
        cursorRect.left = view.getPaddingLeft() + (viewportWidth - cursorWidth) / 2;
        cursorRect.right = cursorRect.right + cursorWidth;
        int cursorHeight = convertToPx(DP_CURSOR_HEIGHT);
        cursorRect.top = (view.getHeight() - cursorHeight) / 2;
        cursorRect.bottom = cursorRect.bottom + cursorHeight;
    }

    int getMarksCount() {
        return marksCount;
    }

    void onDraw(Canvas canvas) {
        double step = 2 * PI / marksCount;
        double offset = (PI / 2 - view.getRadiansAngle()) % step;
        if (offset < 0) {
            offset += step;
        }
        setupGaps(step, offset);
        setupShadesAndScales(step, offset);
        int zeroIndex = calcZeroIndex(step);
        setupColorSwitches(step, offset, zeroIndex);
        drawMarks(canvas, zeroIndex);
        drawCursor(canvas);
    }

    private void setupGaps(double step, double offset) {
        gaps[0] = (float) sin(offset / 2);
        float sum = gaps[0];
        double angle = offset;
        int n = 1;
        while (angle + step <= PI) {
            gaps[n] = (float) sin(angle + step / 2);
            sum += gaps[n];
            angle += step;
            n++;
        }
        float lastGap = (float) sin((PI + angle) / 2);
        sum += lastGap;
        if (n != gaps.length) {
            gaps[gaps.length - 1] = -1;
        }
        float k = view.getWidth() / sum;
        for (int i = 0; i < gaps.length; i++) {
            if (gaps[i] != -1) {
                gaps[i] *= k;
            }
        }
    }

    private void setupShadesAndScales(double step, double offset) {
        double angle = offset;
        for (int i = 0; i < maxVisibleMarksCount; i++) {
            double sin = sin(angle);
            shades[i] = (float) (1 - SHADE_RANGE * (1 - sin));
            scales[i] = (float) (1 - SCALE_RANGE * (1 - sin));
            angle += step;
        }
    }

    private int calcZeroIndex(double step) {
        double twoPi = 2 * PI;
        double normalizedAngle = (view.getRadiansAngle() + PI / 2 + twoPi) % twoPi;
        if (normalizedAngle > PI) {
            return -1;
        }
        return (int) ((PI - normalizedAngle) / step);
    }

    private void setupColorSwitches(double step, double offset, int zeroIndex) {
        if (!showActiveRange) {
            Arrays.fill(colorSwitches, -1);
            return;
        }
        double angle = view.getRadiansAngle();
        int afterMiddleIndex = 0;
        if (offset < PI / 2) {
            afterMiddleIndex = (int) ((PI / 2 - offset) / step) + 1;
        }
        if (angle > 3 * PI / 2) {
            colorSwitches[0] = 0;
            colorSwitches[1] = afterMiddleIndex;
            colorSwitches[2] = zeroIndex;
        } else if (angle >= 0) {
            colorSwitches[0] = Math.max(0, zeroIndex);
            colorSwitches[1] = afterMiddleIndex;
            colorSwitches[2] = -1;
        } else if (angle < -3 * PI / 2) {
            colorSwitches[0] = 0;
            colorSwitches[1] = zeroIndex;
            colorSwitches[2] = afterMiddleIndex;
        } else if (angle < 0) {
            colorSwitches[0] = afterMiddleIndex;
            colorSwitches[1] = zeroIndex;
            colorSwitches[2] = -1;
        }
    }

    private void drawMarks(Canvas canvas, int zeroIndex) {
        float y = view.getPaddingTop();
        int color = normalColor;
        int colorPointer = 0;
        for (int i = 0; i < gaps.length; i++) {
            if (gaps[i] == -1) {
                break;
            }
            y += gaps[i];
            while (colorPointer < 3 && i == colorSwitches[colorPointer]) {
                color = color == normalColor ? activeColor : normalColor;
                colorPointer++;
            }
            if (i != zeroIndex) {
                drawNormalMark(canvas, y, scales[i], shades[i], color);
            } else {
                drawZeroMark(canvas, y, scales[i], shades[i]);
            }
        }
    }

    private void drawNormalMark(Canvas canvas, float y, float scale, float shade, int color) {
        float width = normalMarkWidth * scale;
        float left = view.getPaddingLeft() + (viewportWidth - width) / 2;
        float right = left + width;
        paint.setStrokeWidth(normalMarkHeight);
        paint.setColor(applyShade(color, shade));
        canvas.drawLine(y, left, y, right, paint);
    }

    private int applyShade(int color, float shade) {
        int r = (int) (Color.red(color) * shade);
        int g = (int) (Color.green(color) * shade);
        int b = (int) (Color.blue(color) * shade);
        return Color.rgb(r, g, b);
    }

    private void drawZeroMark(Canvas canvas, float y, float scale, float shade) {
        float width = zeroMarkWidth * scale;
        float left = view.getPaddingLeft() + (viewportWidth - width) / 2;
        float right = left + width;
        paint.setStrokeWidth(zeroMarkHeight);
        paint.setColor(applyShade(activeColor, shade));
        canvas.drawLine(y, left, y, right, paint);
    }

    private void drawCursor(Canvas canvas) {
        paint.setStrokeWidth(0);
        paint.setColor(activeColor);
        canvas.drawRoundRect(cursorRect, cursorCornersRadius, cursorCornersRadius, paint);
    }

}
