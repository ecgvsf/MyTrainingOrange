package com.example.mytrainingorange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.mytrainingorange.R;
import com.example.mytrainingorange.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class PAdapterMenu extends PagerAdapter {
    private List<Recipe> recipes;
    private LayoutInflater layoutInflater;
    private Context context;

    public PAdapterMenu(List<Recipe> recipes, Context context) {
        this.recipes = recipes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item2, container, false);

        ArrayList<ImageView> images = new ArrayList();
        ArrayList<TextView> titles = new ArrayList();
        ArrayList<TextView> descriptions = new ArrayList();


        images.add(view.findViewById(R.id.image1l));
        titles .add( view.findViewById(R.id.title1l));
        descriptions .add( view.findViewById(R.id.description1l));

        images.add(view.findViewById(R.id.image2l));
        titles .add( view.findViewById(R.id.title2l));
        descriptions .add( view.findViewById(R.id.description2l));

        images.add(view.findViewById(R.id.image3l));
        titles .add( view.findViewById(R.id.title3l));
        descriptions .add( view.findViewById(R.id.description3l));

        images.add(view.findViewById(R.id.image4l));
        titles .add( view.findViewById(R.id.title4l));
        descriptions .add( view.findViewById(R.id.description4l));

        images.add(view.findViewById(R.id.image5l));
        titles .add( view.findViewById(R.id.title5l));
        descriptions .add( view.findViewById(R.id.description5l));

        images.add(view.findViewById(R.id.image1r));
        titles .add( view.findViewById(R.id.title1r));
        descriptions .add( view.findViewById(R.id.description1r));

        images.add(view.findViewById(R.id.image2r));
        titles .add( view.findViewById(R.id.title2r));
        descriptions .add( view.findViewById(R.id.description2r));

        images.add(view.findViewById(R.id.image3r));
        titles .add( view.findViewById(R.id.title3r));
        descriptions .add( view.findViewById(R.id.description3r));

        images.add(view.findViewById(R.id.image4r));
        titles .add( view.findViewById(R.id.title4r));
        descriptions .add( view.findViewById(R.id.description4r));

        images.add(view.findViewById(R.id.image5r));
        titles .add( view.findViewById(R.id.title5r));
        descriptions .add( view.findViewById(R.id.description5r));

        for (int i=0; i < images.size(); i++) {
            images.get(i).setImageResource(recipes.get(position).getImage());
            titles.get(i).setText(recipes.get(position).getTitle());
            descriptions.get(i).setText(recipes.get(position).getTitle());
        }

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
