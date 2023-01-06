package ru.skillbox.a32_36_materialdesign.app

import android.app.Application
import ru.skillbox.a32_36_materialdesign.BuildConfig
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}