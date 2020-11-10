package com.example.weather.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object CityService{
    private var retrofit = Retrofit.Builder()
        .baseUrl(MyConstants.BaseCityUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun <T> create(serviceClass: Class<T>) = retrofit.create(serviceClass)
}

object WeatherService{
    private var retrofit = Retrofit.Builder()
        .baseUrl(MyConstants.BaseWeatherUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun <T> create(serviceClass: Class<T>) = WeatherService.retrofit.create(serviceClass)
}


