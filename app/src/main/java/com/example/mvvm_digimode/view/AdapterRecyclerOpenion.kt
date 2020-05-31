package com.example.mvvm_digimode.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_digimode.R
import com.example.mvvm_digimode.Utils.ProductViewHolder
import com.example.mvvm_digimode.model.Openion_Model
import kotlinx.android.synthetic.main.openion_item.view.*

class AdapterRecyclerOpenion (private val openionList:MutableList<Openion_Model>)
    : RecyclerView.Adapter<ProductViewHolder>() {

    lateinit var mcontext: Context

    override fun getItemCount(): Int=openionList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        mcontext=parent.context
        return ProductViewHolder(
            LayoutInflater.from(mcontext)
                .inflate(R.layout.openion_item,parent,false))

    }



    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val openionData=openionList[position]

        val msg_title=holder.itemView.show_msg_title_row
        msg_title.setText(openionData.title_msg)

        val msg_context=holder.itemView.show_msg_context_row
        msg_context.setText(openionData.context_msg)

        val msg_username=holder.itemView.show_msg_username_row
        msg_username.setText(openionData.username)

        val msg_status=holder.itemView.show_msg_status_row
        if (openionData.status.equals("بد")){
            msg_status.setText("خرید این محصول را توصیه نمی کنم")
            msg_status.setTextColor(mcontext.resources.getColor(R.color.red500))
            msg_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_thumb_down_red_24dp, 0)
        }else  if (openionData.status.equals("خوب")){
            msg_status.setText("خرید این محصول را توصیه می کنم")
            msg_status.setTextColor(mcontext.resources.getColor(R.color.greenface))
            msg_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_thumb_up_green_24dp, 0)

        }else  if (openionData.status.equals("معمولی")){
            msg_status.setText("در مورد خرید این محصول مطمئن نیستم")
            msg_status.setTextColor(mcontext.resources.getColor(R.color.grey600))
            msg_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.question_gray_logo, 0)

        }

    }
}
