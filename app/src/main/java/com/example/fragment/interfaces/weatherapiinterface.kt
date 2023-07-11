package com.example.fragment.interfaces

import com.example.fragment.weatherallclassess.Weatherapi
import retrofit2.http.GET
import retrofit2.http.Query

interface weatherapiinterface {

    @GET("forecast")
    fun getWeatherData(@Query("q") city: String,
                       @Query("appid") appid: String, @Query("units") units: String): retrofit2.Call<Weatherapi>
}