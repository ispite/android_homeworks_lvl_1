package ru.skillbox.a30_34_flow.app

import android.app.Application
import ru.skillbox.a30_34_flow.BuildConfig
import timber.log.Timber

class FlowApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}