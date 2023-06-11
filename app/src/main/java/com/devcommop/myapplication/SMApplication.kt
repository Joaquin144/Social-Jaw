package com.devcommop.myapplication

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SMApplication: Application() {
    companion object{
        //todo: Use smth better than this. It can cause memory leak
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}