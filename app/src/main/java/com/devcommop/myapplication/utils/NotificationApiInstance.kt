package com.devcommop.myapplication.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NotificationApiInstance {
    companion object{
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(Constants.FCM_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api by lazy{
            retrofit.create(NotificationApi::class.java)
        }
    }
}