package com.example.weather

import android.view.View

abstract class BasePresenter {
    open var mView :BaseView? = null

    fun attach(view: BaseView) {
        mView = view
    }

    fun detach() {
        mView = null
    }
}