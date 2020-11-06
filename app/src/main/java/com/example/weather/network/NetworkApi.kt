package com.example.weather.network


import com.example.weather.bean.AirData
import com.example.weather.bean.CityInfo
import com.example.weather.bean.WarningData
import com.example.weather.bean.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

public interface NetworkApi {

    @GET("v2/city/lookup?")
    fun getLocation(@Query("location")  location: String, @Query("key") key: String): Call<CityInfo>

    @GET("v7/weather/now?")
    fun getLiveWeatherData(@Query("location") location: String, @Query("key") key: String): Call<WeatherData>

    @GET("v7/air/now?")
    fun getAirData(@Query("location") location: String, @Query("key") key: String): Call<AirData>

    @GET("v7/warning/now?")
    fun getWarning(@Query("location") location: String, @Query("key") key: String): Call<WarningData>

}