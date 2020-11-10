package com.example.weather.bean

data class ThreeDaysWeatherData(
    val code: String,
    val daily: List<Daily>,
    val fxLink: String,
    val refer: Refer,
    val updateTime: String
)

data class Daily(
    val fxDate: String,
    val humidity: String,
    val iconDay: String,
    val iconNight: String,
    val moonrise: String,
    val moonset: String,
    val precip: String,
    val pressure: String,
    val sunrise: String,
    val sunset: String,
    val tempMax: String,
    val tempMin: String,
    val textDay: String,
    val textNight: String,
    val uvIndex: String,
    val vis: String,
    val wind360Day: String,
    val wind360Night: String,
    val windDirDay: String,
    val windDirNight: String,
    val windScaleDay: String,
    val windScaleNight: String,
    val windSpeedDay: String,
    val windSpeedNight: String
)

data class Refer(
    val license: List<String>,
    val sources: List<String>
)