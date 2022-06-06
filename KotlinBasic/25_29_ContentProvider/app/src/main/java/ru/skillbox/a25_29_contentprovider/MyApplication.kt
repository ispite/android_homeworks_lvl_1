package ru.skillbox.a25_29_contentprovider

import android.app.Application
import android.util.Log

class MyApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("MyApplication", "onCreate: Start ContentProvider Application")
    }
}