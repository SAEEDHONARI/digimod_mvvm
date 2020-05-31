package com.example.mvvm_digimode.Utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object glideimage {
    @JvmStatic
    @BindingAdapter("image")
    fun Getimage(view:ImageView,url:String){
        Glide
            .with(view)
            .load(url)
            .centerInside()
            .into(view)
    }
}