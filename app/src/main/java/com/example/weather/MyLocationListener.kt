package com.example.weather

import android.os.Handler
import android.os.Message
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation


class MyLocationListener(var mHandler: Handler) : BDAbstractLocationListener() {
    override fun onReceiveLocation(bdLocation: BDLocation?) {
        if (bdLocation == null) {
            return
        }
//        var country : String = bdLocation.country
//        var province :String = bdLocation.province
        var city: String = bdLocation.city
        var msg: Message = Message()
        msg.what = 1
        msg.obj = city
        mHandler.sendMessage(msg)
    }


}

