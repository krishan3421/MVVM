package com.mvvm.data.networks

import com.mvvm.data.networks.responses.AutResponse
import com.mvvm.data.networks.responses.QuotesResponse
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MyApi {
    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(@Field("email") email:String, @Field("password") password:String): Response<AutResponse>

    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignup(@Field("name") name:String,
                           @Field("email") email:String,
                           @Field("password") password:String):Response<AutResponse>
    @GET("quotes")
    suspend fun getQuotes():Response<QuotesResponse>

    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ):MyApi{
            val okHttpClient=OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                    .baseUrl("https://api.simplifiedcoding.in/course-apis/mvvm/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(MyApi::class.java)
        }
    }
}