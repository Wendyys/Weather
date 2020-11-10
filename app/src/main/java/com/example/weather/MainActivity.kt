package com.example.weather

import android.app.ActionBar
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.SyncStateContract
import android.text.format.Time
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baidu.location.*
import com.example.weather.bean.*
import com.example.weather.network.NetworkApi
import com.example.weather.utils.CityService
import com.example.weather.utils.MyConstants
import com.example.weather.utils.WeatherService
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
    private var mContentPanel : LinearLayout? = null
    private var mWeatherCity : TextView? = null
    private var mWeatherTemperture : TextView? = null
    private var mWeatherDesc : TextView? = null
    private var mWeatherIcon : ImageView? = null
    private var mWeatherWind : TextView? = null
    private var mWeatherWet : TextView? = null
    private var mWeatherList : RecyclerView? = null
    private var mWeatherAir : TextView? = null
    private var locationService: NetworkApi =CityService.create(NetworkApi::class.java)
    private var weatherService: NetworkApi = WeatherService.create(NetworkApi::class.java)

    var TAG :String = "VVWeather"
    //百度地图获取的城市名字
    private var cityName : String? = null
    //和风天气获取的城市id
    private var locationId: String? = null

    private var listAdapter : WeatherListAdapter? = null
    private var mHandler : Handler = Handler{msg ->
        when(msg.what){
            1 ->{
                cityName = msg.obj.toString()
                mWeatherCity?.text = cityName
                getCityLocation(cityName!!)

            }
            2 ->{
                locationId = msg.obj.toString()
                locationId?.let {
                    getWeatherInfo(it)
                    getThreeDaysWeatherInfo(it)
                }

            }
        }
        false
    }
    private var myListener: MyLocationListener = MyLocationListener(mHandler)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        setBackgroundAndScreen()
        getLocation()


    }
    //根据城市名称获取城市代码
    private fun getCityLocation(cityName:String){
        var  call : Call<CityInfo> = locationService.getLocation(cityName, MyConstants.WeatherKey)
        call.enqueue(object : Callback<CityInfo>{
            override fun onResponse(
                call: Call<CityInfo>,
                response: Response<CityInfo>
            ) {
                var locations = response.body()?.location
                if(locations!= null && locations.isNotEmpty()){
                    var msg : Message = Message()
                    msg.what = 2
                    msg.obj = locations[0].id
                    mHandler.sendMessage(msg)
               }
            }

            override fun onFailure(call: Call<CityInfo>, t: Throwable) {
                Log.d("getLocationFailed", t.toString())
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
    //获取城市天气状况
    private fun getWeatherInfo(locationId:String){
        val call :Call<WeatherData> = weatherService.getLiveWeatherData(locationId,MyConstants.WeatherKey)
        call.enqueue(object :Callback<WeatherData>{
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                var now = response.body()?.now
                if (now != null) {
                    setData(now)
                }
            }

            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                Log.d("getWeatherFailed", t.toString())
            }

        })
    }
    private fun getThreeDaysWeatherInfo(locationId:String){
        val call : Call<ThreeDaysWeatherData> = weatherService.getThreeDaysWeatherData(locationId,MyConstants.WeatherKey)
        call.enqueue(object :Callback<ThreeDaysWeatherData>{
            override fun onResponse(call: Call<ThreeDaysWeatherData>, response: Response<ThreeDaysWeatherData>) {
                Log.d(TAG,response.body()?.daily.toString())
                listAdapter = response.body()?.daily?.let { WeatherListAdapter(it,baseContext) }
                mWeatherList?.adapter = listAdapter
                mWeatherList?.layoutManager = LinearLayoutManager(baseContext)
            }

            override fun onFailure(call: Call<ThreeDaysWeatherData>, t: Throwable) {
                Log.d(TAG, t.toString())
            }

        })
    }
    private fun initView(){
        mContentPanel = findViewById(R.id.contentPanel)
        mWeatherCity = findViewById(R.id.weather_overview_city)
        mWeatherTemperture = findViewById(R.id.weather_overview_temperature)
        mWeatherDesc = findViewById(R.id.weather_overview_description)
        mWeatherWind = findViewById(R.id.weather_overview_wind)
        mWeatherList = findViewById(R.id.weather_list)
        mWeatherAir = findViewById(R.id.weather_air)

    }
    //根据获取到的天气情况设置view
    private fun setData(now : WeatherData.Now){
        mWeatherDesc?.text = now.text
        mWeatherTemperture?.text = now.temp
        mWeatherWind?.text = now.windDir
        mWeatherWet?.text = "相对湿度："+now.humidity+"%"
        var iconName = "icon"+now.icon
        //这里不能写mipmap-xxxhdpi 会找不到资源
        var iconRes = baseContext.resources.getIdentifier(iconName,"mipmap",packageName)
        mWeatherIcon?.setImageResource(iconRes)
    }
    //根据时间设置app的背景图片
    private fun setBackgroundAndScreen(){
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        var curTime = Calendar.getInstance()
        curTime.timeInMillis = System.currentTimeMillis()
        var curTimeHour = curTime.get(Calendar.HOUR_OF_DAY)
        if(curTimeHour <= 7 || curTimeHour >= 21){
            //夜晚
            mContentPanel?.setBackgroundResource(R.mipmap.night)
        }else if (curTimeHour < 16){
            //白天
            mContentPanel?.setBackgroundResource(R.mipmap.day)
        }else{
            //傍晚
            mContentPanel?.setBackgroundResource(R.mipmap.afternoon)
        }
    }
}