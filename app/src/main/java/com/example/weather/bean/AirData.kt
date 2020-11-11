package com.example.weather.bean

data class AirData(
    val code: String,
    val fxLink: String,
    val now: Now,
    val refer: Refer,
    val station: List<Station>,
    val updateTime: String
) {
    data class Now(
        val aqi: String,
        val category: String,
        val co: String,
        val no2: String,
        val o3: String,
        val pm10: String,
        val pm2p5: String,
        val primary: String,
        val pubTime: String,
        val so2: String
    )

    data class Refer(
        val license: List<String>,
        val sources: List<String>
    )

    data class Station(
        val aqi: String,
        val category: String,
        val co: String,
        val level: String,
        val no2: String,
        val o3: String,
        val pm10: String,
        val pm2p5: String,
        val primary: String,
        val pubTime: String,
        val so2: String,
        val stationId: String,
        val stationName: String
    )
}

