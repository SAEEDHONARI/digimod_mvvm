package com.example.mvvm_digimode.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_digimode.R
import com.example.mvvm_digimode.Utils.ClickItem
import com.example.mvvm_digimode.databinding.SingleProductBinding
import com.example.mvvm_digimode.model.Product_Model
import kotlinx.android.synthetic.main.single_product.view.*
import java.math.RoundingMode
import java.text.DecimalFormat


class Adapter_Horizontal_Recycler(val list : List<Product_Model>, val click: ClickItem) : RecyclerView.Adapter<Adapter_Horizontal_Recycler.viewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        return viewholder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.single_product,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return  list.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val data=list.get(position)
        holder.items.data=data

        //////////////

        val decimalFormat = DecimalFormat("0,000")
        decimalFormat.roundingMode = RoundingMode.CEILING

        val price = data.price.trim().toInt()
        var productPrice=decimalFormat.format(data.price.trim().toInt())


        val txt_price=holder.itemView.txt_price_product
        txt_price.setText(productPrice + " تومان")


        holder.itemView.setOnClickListener({
            click.ClickItem(data.iD,data.barcode,data.category,data.des,data.discount,data.pic1,data.pic2,data.pic3,data.pic4,data.price,data.title)

        })
    }

    class viewholder(val items:SingleProductBinding) : RecyclerView.ViewHolder(items.root)


}