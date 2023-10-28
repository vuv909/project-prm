package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.myapplication.R;

import org.w3c.dom.Text;

public class SliderAdapter extends PagerAdapter {
    Context context;

    LayoutInflater layoutInflater;
    public SliderAdapter(Context context){
        this.context = context;
    }

    int imgs[] = {
            R.drawable.onboardscreen1,
            R.drawable.onboardscreen2,
            R.drawable.onboardscreen3
    };

    int headingArray[] = {
            R.string.first_slide,R.string.second_slide,R.string.third_slide
    };
    int descriptionArray[] = {
            R.string.description,R.string.description,R.string.description
    };
    @Override
    public int getCount() {
        return headingArray.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.sliding_layout,container,false);

        ImageView imgView = view.findViewById((R.id.slider_img));
        TextView tview = view.findViewById(R.id.heading);
        TextView desc = view.findViewById(R.id.description);

        imgView.setImageResource(imgs[position]);
        tview.setText(headingArray[position]);
        desc.setText(descriptionArray[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
