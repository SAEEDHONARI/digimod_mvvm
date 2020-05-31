package com.example.mvvm_digimode.Utils

interface ClickItem {
    fun ClickItem(
        id: String,
        barcode: String,
        category: String,
        des: String,
        discount: Int,
        pic1: String,
        pic2: String,
        pic3: String,
        pic4: String,
        price: String,
        title: String
    )
}