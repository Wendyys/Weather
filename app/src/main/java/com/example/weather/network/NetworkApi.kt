package com.example.weather.network


import com.example.weather.bean.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

public interface NetworkApi {
    //获取城市ID
    @GET("v2/city/lookup?")
    fun getLocation(@Query("location")  location: String, @Query("key") key: String): Call<CityInfo>
    //获取实况天气
    @GET("v7/weather/now?")
    fun getLiveWeatherData(@Query("location") location: String, @Query("key") key: String): Call<WeatherData>
    //获取空气质量
    @GET("v7/air/now?")
    fun getAirData(@Query("location") location: String, @Query("key") key: String): Call<AirData>
    //获取预警
    @GET("v7/warning/now?")
    fun getWarning(@Query("location") location: String, @Query("key") key: String): Call<WarningData>
    //获取3天天气预报
    @GET("v7/weather/3d?")
    fun getThreeDaysWeatherData(@Query("location") location: String, @Query("key") key: String): Call<ThreeDaysWeatherData>
}