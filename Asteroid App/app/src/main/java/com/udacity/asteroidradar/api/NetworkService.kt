package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface NetworkService {
    @GET("neo/rest/v1/feed/")
    suspend fun getAsteroids(
        @Query("api_key") key : String ="DEMO_KEY"
    ): String

    @GET("planetary/apod/")
    suspend fun getPictureOfDay(
        @Query("api_key")
        apiKey: String) : PictureOfDay
}







object Api{
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val retrofitService : NetworkService by lazy {
        retrofit.create(NetworkService::class.java)
    }
}





