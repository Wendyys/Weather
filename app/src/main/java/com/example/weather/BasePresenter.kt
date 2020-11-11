package com.example.weather

/**
 * 使用抽象类而不是接口，是因为可以写一些方法的默认实现，且抽象类的改动较小
 * 而View使用接口，主要是一般实现接口的是Activity或者Fragment，不符合继承的概念
 */
abstract class BasePresenter<T : BaseView>{
    open var mView :WeatherView? = null

    fun attach(view: WeatherView) {
        mView = view
    }

    fun detach() {
        mView = null
    }
}