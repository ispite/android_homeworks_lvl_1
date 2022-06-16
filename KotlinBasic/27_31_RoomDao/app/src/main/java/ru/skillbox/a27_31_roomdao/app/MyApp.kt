package ru.skillbox.a27_31_roomdao.app

import android.app.Application
import ru.skillbox.a27_31_roomdao.BuildConfig
import ru.skillbox.a27_31_roomdao.data.db.Database
import timber.log.Timber

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Database.init(this)
    }
}