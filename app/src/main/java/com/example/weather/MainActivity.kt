package com.example.weather

import android.os.Bundle
import android.os.Handler
import android.provider.SyncStateContract
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.baidu.location.*
import com.example.weather.bean.CityInfo
import com.example.weather.network.NetworkApi
import com.example.weather.utils.CityService
import com.example.weather.utils.MyConstants
import com.google.gson.Gson
import com.hjq.permissions.OnPermission
import com.hjq.permissions.XXPermissions
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(){
    lateinit var mLocationClient : LocationClient
    private var mWeatherCity : TextView? = null
    private var mWeatherTemperture : TextView? = null
    private var mWeatherDesc : TextView? = null
    private var mWeatherIcon : ImageView? = null
    private var mWeatherWind : TextView? = null
    private var mWeatherSun : TextView? = null
    private var mWeatherWet : TextView? = null
    private var mWeatherList : RecyclerView? = null
    private var mWeatherAir : TextView? = null
    private var mWeatherWarning : TextView? = null
    private var cityName : String? = null

    private var mHandler : Handler = Handler{
        when(it.what){
            1 ->{
                cityName = it.obj.toString()
                mWeatherCity?.text = cityName
                getCityLocation(cityName!!)
            }
        }
        false
    }
    private var myListener: MyLocationListener = MyLocationListener(mHandler)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        getLocation()

    }
    //根据城市名称获取城市代码
    private fun getCityLocation(cityName:String){
        var locationService =CityService.create(NetworkApi::class.java)
        var  call : Call<CityInfo> = locationService.getLocation(cityName, MyConstants.WeatherKey)
        call.enqueue(object : Callback<CityInfo>{
            override fun onResponse(
                call: Call<CityInfo>,
                response: Response<CityInfo>
            ) {
                Log.d("VVWeather", response.body().toString())
            }

            override fun onFailure(call: Call<CityInfo>, t: Throwable) {
                Log.d("VVWeather", t.toString())
            }


        })
    }
    //获取城市名称
    private fun getLocation(){
        mLocationClient = LocationClient(applicationContext)
        mLocationClient.registerLocationListener(myListener)
        var option : LocationClientOption = LocationClientOption()
        option.setIsNeedAddress(true)
        option.setNeedNewVersionRgc(true)
        option.setIsNeedLocationDescribe(true)
        mLocationClient.locOption = option
        mLocationClient.start()
    }

    private fun initView(){
        mWeatherCity = findViewById(R.id.weather_overview_city)
        mWeatherTemperture = findViewById(R.id.weather_overview_temperature)
        mWeatherDesc = findViewById(R.id.weather_overview_description)
        mWeatherIcon = findViewById(R.id.weather_overview_icon)
        mWeatherWind = findViewById(R.id.weather_overview_wind)
        mWeatherSun = findViewById(R.id.weather_overview_sunny)
        mWeatherWet = findViewById(R.id.weather_overview_wet)
        mWeatherList = findViewById(R.id.weather_list)
        mWeatherAir = findViewById(R.id.weather_air)
        mWeatherWarning = findViewById(R.id.weather_waring)

    }

}