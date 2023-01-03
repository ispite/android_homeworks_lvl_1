package ru.skillbox.a31_35_backgroundwork.app

import android.app.Application
import ru.skillbox.a31_35_backgroundwork.BuildConfig
import timber.log.Timber

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}