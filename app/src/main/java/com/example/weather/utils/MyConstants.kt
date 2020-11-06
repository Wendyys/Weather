package com.example.weather.utils

/**
 * 存放常用变量
 */
class MyConstants {
    companion object{
        var BaiduMapKey = "Y7b6N0ziD3vSvuvdIaYE85gbZFKphv3S"
        var WeatherKey = "e50f48652b5b403d9bfa494ffdc6f1c8"

        var BaseCityUrl : String = "https://geoapi.qweather.com/"
        var BaseWeatherUrl : String = "https://devapi.qweather.com/v7/"
        //获取 城市代号   location/key
        var GetLocationCodeUrl = "v2/city/lookup?"
        //获取实况天气  location/key
        var GetLiveWeatherDataUrl = "v7/weather/now?"
        //获取空气质量状况  location/key
        var GetAirDataUrl = "v7/air/now?"
        //获取实时灾害预警 location/key
        var GetWarningDataUrl = "v7/warning/now?"
    }

}