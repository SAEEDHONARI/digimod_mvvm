package com.example.mvvm_digimode.Repository

import com.example.mvvm_digimode.model.Banner_Model
import com.example.mvvm_digimode.model.Openion_Model
import com.example.mvvm_digimode.model.Product_Model
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {

    @GET("get_discount_product.php")
    fun GetDiscountProduct():Single<List<Product_Model>>


    @GET("get_seison1.php")
    fun GetSeison():Single<List<Product_Model>>

    @GET("get_product_banners.php")
    fun GetBanner():Single<List<Banner_Model>>

    @FormUrlEncoded
    @POST("get_the_most_sales_product.php")
    fun GetMostSell(@Field("sale_number")sale_number : String):Single<List<Product_Model>>

    @FormUrlEncoded
    @POST("get_openion.php")
    fun GetOpenion(@Field("id_product")id_product:String):Single<MutableList<Openion_Model>>

    @FormUrlEncoded
    @POST("")
    fun SendOpenion(
     @Field("username")username:String
    ,@Field("title_msg")title_msg:String
    ,@Field("context_msg")context_msg:String
    ,@Field("id_product")id_product:String
    ,@Field("status")status:String
    ):Single<String>



    companion object {
        operator fun invoke() : Api{
            return Retrofit.Builder()
                .baseUrl("http://honarijamshid.cloudsite.ir/dgmod/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api::class.java)
        }

    }
}