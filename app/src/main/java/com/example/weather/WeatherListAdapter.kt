package com.example.weather

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.bean.Daily

class WeatherListAdapter(private val entityList : List<Daily>,private val context:Context) : RecyclerView.Adapter<WeatherListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_list_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val entity = entityList[position]
        holder.mListDay.text = entity.fxDate
        holder.mListWeather.text = entity.textDay
        holder.mListTemp.text = entity.tempMin +" ~ "+entity.tempMax+"℃"
        holder.mListWind.text = entity.windDirDay
        var iconName = "icon"+entity.iconDay
        var iconRes = context.resources.getIdentifier(iconName,"mipmap",context.packageName)
        holder.mListIcon.setImageResource(iconRes)



    }

    override fun getItemCount(): Int {
        return entityList.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mListIcon :ImageView = itemView.findViewById(R.id.list_icon)
         var mListDay = itemView.findViewById<TextView>(R.id.list_day)
         var mListWeather = itemView.findViewById<TextView>(R.id.list_weather)
         var mListWind = itemView.findViewById<TextView>(R.id.list_wind)
         var mListTemp= itemView.findViewById<TextView>(R.id.list_temp)

    }

}