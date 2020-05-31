package com.example.mvvm_digimode.view.MainSlider;


import android.view.View;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.mvvm_digimode.R;
import com.example.mvvm_digimode.view.CornerImageView;
import com.zhpan.bannerview.BaseViewHolder;

public class ImageResourceViewHolder extends BaseViewHolder<String> {

    public ImageResourceViewHolder(@NonNull View itemView, int roundCorner) {
        super(itemView);
        CornerImageView imageView = findView(R.id.banner_image);
        imageView.setRoundCorner(roundCorner);
    }

    @Override
    public void bindData(String data, int position, int pageSize) {
        CornerImageView imageView = findView(R.id.banner_image);

        //   setImageResource(R.id.banner_image, data);
        Glide.with(imageView).load(data).into(imageView);
    }
}
