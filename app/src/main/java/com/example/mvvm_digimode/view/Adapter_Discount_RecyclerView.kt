package com.example.mvvm_digimode.view

import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_digimode.R
import com.example.mvvm_digimode.Utils.ClickItem
import com.example.mvvm_digimode.databinding.ItemDiscountBinding
import com.example.mvvm_digimode.model.Product_Model
import kotlinx.android.synthetic.main.item_discount.view.*
import java.math.RoundingMode
import java.text.DecimalFormat


class Adapter_Discount_RecyclerView(val list : List<Product_Model> , val click:ClickItem) : RecyclerView.Adapter<Adapter_Discount_RecyclerView.viewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        return viewholder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_discount,
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


        val toman: String =" تومان "

        val PriceSize = (productPrice.toString().length).plus(toman.length)

        var productdiscount :String
        val discount_Img=holder.itemView.img_discount
        val txt_price = holder.itemView.txt_circle_recycle_price

        val spannableString = SpannableString(productPrice.toString().plus(" تومان"))
        spannableString.setSpan(StrikethroughSpan(),0,PriceSize-1, Spanned.SPAN_MARK_MARK)

        val txt_discount = holder.itemView.txt_circle_recycle_discount
        txt_price.setText(spannableString)


        when(data.discount){
            10 ->  {discount_Img.setImageResource(R.drawable.discount10)
                productdiscount = (price -(price* 0.1)).toString()

            }
            15 -> {discount_Img.setImageResource(R.drawable.discount15)
                productdiscount = (price-(price * 0.15)).toString()
            }
            20 ->  {discount_Img.setImageResource(R.drawable.discount20)
                productdiscount = (price-(price * 0.2)).toString()
            }
            25 ->  {discount_Img.setImageResource(R.drawable.discount25)
                productdiscount = (price-(price * 0.25)).toString()
            }
            30 ->  {discount_Img.setImageResource(R.drawable.discount30)
                productdiscount = (price-(price * 0.30)).toString()
            }
            35 ->  {discount_Img.setImageResource(R.drawable.discount35)
                productdiscount = (price-(price * 0.35)).toString()
            }
            40 ->  {discount_Img.setImageResource(R.drawable.discount40)
                productdiscount = (price-(price *  0.40)).toString()
            }
            45 -> {discount_Img.setImageResource(R.drawable.discount45)
                productdiscount = (price-(price *  0.45)).toString()
            }
            50 ->  {discount_Img.setImageResource(R.drawable.discount50)
                productdiscount = (price-(price *  0.50)).toString()
            }
            55 -> {discount_Img.setImageResource(R.drawable.discount55)
                productdiscount =(price-(price *  0.55)).toString()
            }
            60 ->  {discount_Img.setImageResource(R.drawable.discount60)
                productdiscount =(price-(price *  0.6)).toString()
            }
            70 ->  {discount_Img.setImageResource(R.drawable.discount70)
                productdiscount = (price-(price *  0.7)).toString()
            }
            80 ->  {discount_Img.setImageResource(R.drawable.discount80)
                productdiscount = (price-(price *  0.8)).toString()
            }
            90 ->  {discount_Img.setImageResource(R.drawable.discount90)
                productdiscount = (price-(price *  0.9)).toString()
            }else -> productdiscount = "1"
        }
        val discount =productdiscount.toDouble()
        val a = decimalFormat.format((discount).toInt())
        txt_discount.setText(a.toString().plus(toman))

        holder.itemView.setOnClickListener({
            click.ClickItem(data.iD,data.barcode,data.category,data.des,data.discount,data.pic1,data.pic2,data.pic3,data.pic4,data.price,data.title)

        })


        }

    class viewholder(val items:ItemDiscountBinding) : RecyclerView.ViewHolder(items.root)


}