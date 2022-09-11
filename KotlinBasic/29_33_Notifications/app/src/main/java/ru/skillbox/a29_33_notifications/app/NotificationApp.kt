package ru.skillbox.a29_33_notifications.app

import android.app.Application
import ru.skillbox.a29_33_notifications.BuildConfig
import ru.skillbox.a29_33_notifications.NotificationChannels
import timber.log.Timber

class NotificationApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        NotificationChannels.create(this)
    }
}