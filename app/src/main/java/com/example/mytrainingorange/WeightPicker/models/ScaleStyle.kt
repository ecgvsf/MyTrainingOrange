package com.example.mytrainingorange.WeightPicker.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ScaleStyle(
    val scaleWidth: Dp = 100.dp,
    val radius: Dp = 550.dp,
    val normalLineColor: Color = Color.LightGray,
    val fiveStepLineColor: Color = Color.White,
    val tenStepLineColor: Color = Color(0xFFFF9800),
    val normalLineLength: Dp = 25.dp,
    val fiveStepLineLength: Dp = 35.dp,
    val tenStepLineLength: Dp = 45.dp,
    val normalLineWidth: Dp = 1.dp,
    val fiveStepLineWidth: Dp = 2.dp,
    val tenStepLineWidth: Dp = 3.dp,
    val scaleIndicatorColor: Color = Color(0xFFFF9800),
    val scaleIndicatorLength: Dp = 220.dp,
    val textSize: TextUnit = 20.sp
)