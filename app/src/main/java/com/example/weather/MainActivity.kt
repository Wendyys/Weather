package com.example.weather

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.bean.AirData
import com.example.weather.bean.ThreeDaysWeatherData
import com.example.weather.bean.WeatherData
import com.hjq.permissions.OnPermission
import com.hjq.permissions.XXPermissions
import java.util.*

class MainActivity : AppCompatActivity(), WeatherView {

    private lateinit var mContentPanel: RelativeLayout
    private lateinit var mWeatherCity: TextView
    private lateinit var mWeatherTemperture: TextView
    private lateinit var mWeatherDesc: TextView
    private lateinit var mWeatherWind: TextView
    private lateinit var mWeatherList: RecyclerView
    private lateinit var mWeatherAir: TextView
    private lateinit var listAdapter: WeatherListAdapter
    private lateinit var mPresenter: MVPPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MVPPresenter(baseContext)
        mPresenter.attach(this)
        requestPermission()
        initView()
        setBackgroundAndScreen()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detach()
    }

    private fun initView() {
        mContentPanel = findViewById(R.id.contentPanel)
        mWeatherCity = findViewById(R.id.weather_overview_city)
        mWeatherTemperture = findViewById(R.id.weather_overview_temperature)
        mWeatherDesc = findViewById(R.id.weather_overview_description)
        mWeatherWind = findViewById(R.id.weather_overview_wind)
        mWeatherList = findViewById(R.id.weather_list)
        mWeatherAir = findViewById(R.id.weather_air)

    }

    //根据时间设置app的背景图片
    private fun setBackgroundAndScreen() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        var curTime = Calendar.getInstance()
        curTime.timeInMillis = System.currentTimeMillis()
        var curTimeHour = curTime.get(Calendar.HOUR_OF_DAY)
        if (curTimeHour <= 7 || curTimeHour >= 21) {
            //夜晚
            mContentPanel?.setBackgroundResource(R.mipmap.night)
        } else if (curTimeHour < 16) {
            //白天
            mContentPanel?.setBackgroundResource(R.mipmap.day)
        } else {
            //傍晚
            mContentPanel?.setBackgroundResource(R.mipmap.afternoon)
        }
    }

    private fun requestPermission() {
        XXPermissions.with(this)
            .constantRequest()
            .request(object : OnPermission {
                override fun hasPermission(granted: MutableList<String>?, isAll: Boolean) {
                    mPresenter.getLocation()
                }

                override fun noPermission(denied: MutableList<String>?, quick: Boolean) {
                    Toast.makeText(this@MainActivity, "无法获取权限，应用无法正常使用", Toast.LENGTH_SHORT).show()
                    XXPermissions.gotoPermissionSettings(this@MainActivity)

                }

            })
    }

    override fun updateWeatherInfo(weatherData: WeatherData) {
        mWeatherDesc.text = weatherData.now.text
        mWeatherTemperture.text = weatherData.now.temp
        mWeatherWind.text = weatherData.now.windDir
    }

    override fun updateCityName(cityInfo: String) {
        mWeatherCity.text = cityInfo
    }

    override fun updateAirInfo(airData: AirData) {
        mWeatherAir.text = airData.now.category
    }

    override fun updateWeatherInfoThreeDays(threeDaysWeatherData: ThreeDaysWeatherData) {
        listAdapter = WeatherListAdapter(threeDaysWeatherData.daily, baseContext)
        mWeatherList.adapter = listAdapter
        mWeatherList.layoutManager = LinearLayoutManager(baseContext)
    }
}