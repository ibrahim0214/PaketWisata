package com.example.paketwisata.app

import com.example.paketwisata.model.Checkout
import com.example.paketwisata.model.ResponModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("phone") notlp : String,
        @Field("password") password : String
    ):Call<ResponModel>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email : String,
        @Field("password") password : String
    ):Call<ResponModel>

    @POST("checkout")
    fun checkout(
        @Body data : Checkout
    ):Call<ResponModel>

    @GET("paket")
    fun getPaket():Call<ResponModel>
}