package com.example.mvvm_digimode.model


import com.google.gson.annotations.SerializedName

data class Product_Model(

    @SerializedName("barcode")
    val barcode: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("des")
    val des: String,
    @SerializedName("ID")
    val iD: String,
    @SerializedName("pic1")
    val pic1: String,
    @SerializedName("pic2")
    val pic2: String,
    @SerializedName("pic3")
    val pic3: String,
    @SerializedName("pic4")
    val pic4: String,
    @SerializedName("discount")
    val discount: Int,
    @SerializedName("price")
    val price: String,
    @SerializedName("title")
    val title: String
)
