package com.example.weather.bean

import android.location.Location
import com.google.gson.annotations.SerializedName

data class CityInfo(val code: String, val location: List<Location>) {

    data class Location(
        var name: String, var id: String,
        var lat: String, var lon: String,
        var adm2: String, var adm1: String,
        var country: String, var tz: String,
        var utcOffset: String, var isDst: String,
        var type: String, var rank: String,
        var fxLink: String
    )
}