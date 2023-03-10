package ru.skillbox.a28_32_scopedstorage.app

import android.app.Application
import ru.skillbox.a28_32_scopedstorage.BuildConfig
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}