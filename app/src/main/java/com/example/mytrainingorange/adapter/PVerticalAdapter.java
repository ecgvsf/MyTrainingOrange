package com.example.mytrainingorange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.mytrainingorange.R;

import java.util.List;

public class PVerticalAdapter extends PagerAdapter {

    private List<ViewPager> viewPager;
    private LayoutInflater layoutInflater;
    private Context context;

    public PVerticalAdapter(List<ViewPager> viewPager, Context context) {
        this.viewPager = viewPager;
        this.context = context;
    }

    @Override
    public int getCount() {
        return viewPager.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.view_pager, container, false);

        ViewPager vP;
        vP = view.findViewById(R.id.pager);
        vP.setAdapter(viewPager.get(position).getAdapter());

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}