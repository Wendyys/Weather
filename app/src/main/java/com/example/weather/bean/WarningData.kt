package com.example.weather.bean

data class WarningData(
    val code: String,
    val fxLink: String,
    val refer: Refer,
    val updateTime: String,
    val warning: List<Warning>
) {
    data class Refer(
        val license: List<String>,
        val sources: List<String>
    )

    data class Warning(
        val endTime: String,
        val id: String,
        val level: String,
        val pubTime: String,
        val startTime: String,
        val status: String,
        val text: String,
        val title: String,
        val type: String
    )
}

