package com.example.mvvm_digimode.model


import com.google.gson.annotations.SerializedName

data class Openion_Model(


    @SerializedName("username")
    val username : String,
    @SerializedName("title_msg")
    val title_msg: String,
    @SerializedName("context_msg")
    val context_msg: String,
    @SerializedName("status")
    val status: String


)
