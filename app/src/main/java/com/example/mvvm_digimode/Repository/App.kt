package com.example.mvvm_digimode.Repository

import android.app.Application
import com.bumptech.glide.request.target.ViewTarget
import com.example.mvvm_digimode.R

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        ViewTarget.setTagId(R.id.banner_image);
    }
}