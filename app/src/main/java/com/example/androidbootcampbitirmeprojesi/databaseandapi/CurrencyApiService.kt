package com.example.androidbootcampbitirmeprojesi.databaseandapi

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://free.currconv.com"
//private const val ApiKey = "7593590112cc067660dc"


private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

interface CurrencyApiService {
    @GET("/api/v7/convert")
    suspend fun getProperties1(@Query("q") type: String, @Query("compact") compact :String, @Query("apiKey") apiKey :String): CurrencyProperty1
    @GET("/api/v7/convert")
    suspend fun getProperties2(@Query("q") type: String, @Query("compact") compact :String, @Query("apiKey") apiKey :String): CurrencyProperty2
    @GET("/api/v7/convert")
    suspend fun getProperties3(@Query("q") type: String, @Query("compact") compact :String, @Query("apiKey") apiKey :String): CurrencyProperty3
}

object CurrencyApi {
    val retrofitService :CurrencyApiService by lazy {
        retrofit.create(CurrencyApiService::class.java)
    }
}
//    @GET("/api/v7/convert?q=TRY_USD,TRY_EUR,TRY_GBP,USD_EUR,USD_GBP,EUR_GBP&compact=ultra&apiKey=$ApiKey")
