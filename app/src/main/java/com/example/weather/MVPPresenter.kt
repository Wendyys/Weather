package com.example.weather

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.Log
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.bytedance.common.utility.collection.WeakHandler
import com.example.weather.bean.AirData
import com.example.weather.bean.CityInfo
import com.example.weather.bean.ThreeDaysWeatherData
import com.example.weather.bean.WeatherData
import com.example.weather.network.NetworkApi
import com.example.weather.network.CityService
import com.example.weather.utils.MyConstants
import com.example.weather.network.WeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MVPPresenter(var context: Context) : BasePresenter(),  WeakHandler.IHandler{
    //private  var mView :BaseView? = null
    private var locationService: NetworkApi = CityService.create(NetworkApi::class.java)
    private var weatherService: NetworkApi = WeatherService.create(NetworkApi::class.java)
    //避免内存泄漏
    private var handler: WeakHandler = WeakHandler(this)
    private var myListener: MyLocationListener = MyLocationListener(handler)
    lateinit var mLocationClient: LocationClient
    private var cityName: String? = null
    var TAG: String = "VVWeather"

    //获取城市名称
    fun getLocation() {
        mLocationClient = LocationClient(context)
        mLocationClient.registerLocationListener(myListener)
        var option: LocationClientOption = LocationClientOption()
        option.setIsNeedAddress(true)
        option.setNeedNewVersionRgc(true)
        option.setIsNeedLocationDescribe(true)
        mLocationClient.locOption = option
        mLocationClient.start()
    }
    //根据城市名称获取城市代码
    private fun getCityLocation(cityName: String) {
        var call: Call<CityInfo> = locationService.getLocation(cityName, MyConstants.WeatherKey)
        call.enqueue(object : Callback<CityInfo> {
            override fun onResponse(
                call: Call<CityInfo>,
                response: Response<CityInfo>
            ) {
                var locations = response.body()?.location
                if (locations != null && locations.isNotEmpty()) {
                    var cityId = locations[0].id
                    getWeatherInfo(cityId)
                    getThreeDaysWeatherInfo(cityId)
                    getAirInfo(cityId)
                }
            }

            override fun onFailure(call: Call<CityInfo>, t: Throwable) {
                Log.d("getLocationFailed", t.toString())
            }


        })
    }
    //获取城市天气状况
    private fun getWeatherInfo(locationId: String) {
        val call: Call<WeatherData> = weatherService.getLiveWeatherData(
            locationId,
            MyConstants.WeatherKey
        )
        call.enqueue(object : Callback<WeatherData> {
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                response.body()?.let { mView?.updateWeatherInfo(it) }
            }

            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                Log.d("getWeatherFailed", t.toString())
            }
        })
    }
    //获取3天天气
    private fun getThreeDaysWeatherInfo(locationId: String) {
        val call: Call<ThreeDaysWeatherData> = weatherService.getThreeDaysWeatherData(
            locationId,
            MyConstants.WeatherKey
        )
        call.enqueue(object : Callback<ThreeDaysWeatherData> {
            override fun onResponse(
                call: Call<ThreeDaysWeatherData>,
                response: Response<ThreeDaysWeatherData>
            ) {
                response.body()?.let { mView?.updateWeatherInfoThreeDays(it) }
            }

            override fun onFailure(call: Call<ThreeDaysWeatherData>, t: Throwable) {
                Log.d(TAG, t.toString())
            }
        })
    }
    //获取空气情况
    private fun getAirInfo(locationId: String) {
        val call: Call<AirData> = weatherService.getAirData(
            locationId,
            MyConstants.WeatherKey
        )
        call.enqueue(object : Callback<AirData> {
            override fun onResponse(
                call: Call<AirData>,
                response: Response<AirData>
            ) {

                response.body()?.let { mView?.updateAirInfo(it) }
            }

            override fun onFailure(call: Call<AirData>, t: Throwable) {
                Log.d(TAG, t.toString())
            }
        })
    }

    override fun handleMsg(msg: Message?) {
        when (msg?.what) {
            1 -> {
                cityName = msg.obj as String

                cityName?.let {
                    mView?.updateCityName(it)
                    getCityLocation(it)
                }

            }
        }
    }
}