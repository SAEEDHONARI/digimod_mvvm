package com.example.mvvm_digimode.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_digimode.Repository.Api
import com.example.mvvm_digimode.Repository.Repository
import com.example.mvvm_digimode.model.Openion_Model
import com.example.mvvm_digimode.model.Product_Model
import io.reactivex.disposables.CompositeDisposable
import retrofit2.http.Field

class ViewModel_Openion : ViewModel()
{
    val mutable_Openion= MutableLiveData<MutableList<Openion_Model>>()
    lateinit var str_sendMsg:String
    val com= CompositeDisposable()

    fun get_Openion_product(id_product:String) {
        Repository.Customeresponse.Request(Api.invoke().GetOpenion(id_product), com) {
            mutable_Openion.value = it
        }
    }

    fun send_openionProduct(username:String,title_msg:String,context_msg:String,id_product:String,status:String){
        Repository.Customeresponse.Request(Api.invoke().SendOpenion(username, title_msg, context_msg, id_product, status),com){
            str_sendMsg=it
        }
    }
}