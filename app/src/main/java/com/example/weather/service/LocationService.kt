package com.example.weather.service

import android.app.Application
import android.location.LocationListener
import com.baidu.location.LocationClient

class LocationService( var context: Application) {
    lateinit var mLocationClient : LocationClient
    lateinit var myListener : LocationListener
    public fun getInstance(){
        mLocationClient = LocationClient(context)
    }
}