package com.example.graduationproject.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;


import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.graduationproject.R;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    ArrayList<Uri> ImageUrls;
    boolean aBoolean1;
    LayoutInflater layoutInflater;

    public ViewPagerAdapter(Context context, ArrayList<Uri> imageUrls,boolean aBoolean) {
        this.context = context;
        ImageUrls = imageUrls;
        aBoolean1 = aBoolean;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ImageUrls.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view=layoutInflater.inflate(R.layout.showimageslayout,container,false);
        ImageView imageView=view.findViewById(R.id.UploadImage);

ProgressBar progressBar=view.findViewById(R.id.progressBar3);

        if (aBoolean1==true){
            progressBar.setVisibility(View.GONE);
        }
        Glide.with(context).load(ImageUrls.get(position)).into(imageView);
//        imageView.setImageURI(ImageUrls.get(position));
        container.addView(view);


        return view ;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view== object;
    }

    @Override
    public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
        ((RelativeLayout)object).removeView(container);
    }
}
