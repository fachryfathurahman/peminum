package com.kelsiraman.peminum.onboardingscreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.kelsiraman.peminum.R;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.logoaja,
            R.drawable.ic_people
    };

    public String[] slide_headings = {
            "Selamat Datang",
            "Hai,\n" +
                    "Saya pendamping hidrasi pribadi anda"
    };

    public String[] slide_desc={
            "Peminum adalah aplikasi asisten minum air pribadi anda. Jangan lupa untuk minum air dan jaga kesehatan.",
            "Takaran minum berdasarkan berat badan dan tinggi badan anda",
    };
    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView = view.findViewById(R.id.contentImageSlider);
        TextView slideHeading = view.findViewById(R.id.heading);
        TextView slideDescription = view.findViewById(R.id.contentSlider);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_desc[position]);

        if(position == (slide_headings.length-1)){
            slideHeading.setTextColor(context.getResources().getColor(R.color.black));
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
