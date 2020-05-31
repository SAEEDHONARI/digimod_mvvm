package com.example.mvvm_digimode.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_digimode.model.Banner_Model
import com.example.mvvm_digimode.model.Product_Model
import com.example.mvvm_digimode.Repository.Api
import com.example.mvvm_digimode.Repository.Repository
import com.ginsberg.cirkle.circular
import io.reactivex.disposables.CompositeDisposable

class ViewModel_listPosts : ViewModel() {

    val mutable_Discount=MutableLiveData<List<Product_Model>>()
    val mutable_Season=MutableLiveData<List<Product_Model>>()
    val mutable_Mostsell=MutableLiveData<List<Product_Model>>()
    val mutable_Banner=MutableLiveData<List<Banner_Model>>()

    val com= CompositeDisposable()

    fun get_banner_product(){
        Repository.Customeresponse.Request(Api.invoke().GetBanner(),com){
            mutable_Banner.value=it

        }
    }


   fun get_Discount_product() {
       Repository.Customeresponse.Request(Api.invoke().GetDiscountProduct(), com) {
           mutable_Discount.value = it
       }
   }

    fun get_Season_Product(){
        Repository.Customeresponse.Request(Api.invoke().GetSeison(),com){
            mutable_Season.value=it
        }
    }


    fun get_Most_sell(sale_number:String){
        Repository.Customeresponse.Request(Api.invoke().GetMostSell(sale_number),com){
            mutable_Mostsell.value=it
        }
    }


    override fun onCleared() {
        com.clear()
        super.onCleared()
    }
}