package com.example.mvvm_digimode.model


import com.google.gson.annotations.SerializedName

data class Banner_Model(
    @SerializedName("action")
    val action: String,
    @SerializedName("ID")
    val iD: String,
    @SerializedName("url")
    val url: String
)