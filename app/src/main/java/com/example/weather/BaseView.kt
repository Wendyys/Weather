package com.example.weather

import com.example.weather.bean.AirData
import com.example.weather.bean.ThreeDaysWeatherData
import com.example.weather.bean.WeatherData

interface BaseView{
    //更新天气概况
    fun updateWeatherInfo(weatherData: WeatherData)
    //更新城市名字
    fun updateCityName(cityInfo: String)
    //更新空气状况
    fun updateAirInfo(airData: AirData)
    //更新列表信息（3天的天气）
    fun updateWeatherInfoThreeDays(threeDaysWeatherData: ThreeDaysWeatherData)
}
