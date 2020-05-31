package com.example.mvvm_digimode.view.MainSlider;

import android.view.View;

import com.example.mvvm_digimode.R;
import com.zhpan.bannerview.BaseBannerAdapter;

/**
 * <pre>
 *   Created by zhpan on 2020/4/5.
 *   Description:
 * </pre>
 */
public class ImageResourceAdapter extends BaseBannerAdapter<String, ImageResourceViewHolder> {

    private int roundCorner;

    public ImageResourceAdapter(int roundCorner) {
        this.roundCorner = roundCorner;
    }


    @Override
    protected void onBind(ImageResourceViewHolder holder, String data, int position, int pageSize) {
        holder.bindData(data, position, pageSize);
    }

    @Override
    public ImageResourceViewHolder createViewHolder(View itemView, int viewType) {
        return new ImageResourceViewHolder(itemView, roundCorner);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_slide_mode;
    }
}
