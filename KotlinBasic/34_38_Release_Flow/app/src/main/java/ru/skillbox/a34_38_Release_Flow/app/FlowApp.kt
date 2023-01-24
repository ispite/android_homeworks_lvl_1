package ru.skillbox.a34_38_Release_Flow.app

import android.app.Application
import ru.skillbox.a34_38_Release_Flow.BuildConfig
import ru.skillbox.a34_38_Release_Flow.data.db.Database
import timber.log.Timber

class FlowApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Database.init(this)
        // Force open database
        // Database.instance.openHelper.writableDatabase
    }
}