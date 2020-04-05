package com.develop.basicarchitecture

import android.app.Application

class TopApplicationClass : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: TopApplicationClass
        fun getInstance(): TopApplicationClass {
            return instance
        }
    }

}