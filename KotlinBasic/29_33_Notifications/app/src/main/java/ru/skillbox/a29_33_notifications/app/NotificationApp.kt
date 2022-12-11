package ru.skillbox.a29_33_notifications.app

import android.app.Application
import com.google.firebase.messaging.FirebaseMessaging
import ru.skillbox.a29_33_notifications.BuildConfig
import ru.skillbox.a29_33_notifications.NotificationChannels
import ru.skillbox.a29_33_notifications.data.db.Database
import timber.log.Timber

class NotificationApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Database.init(this)
        NotificationChannels.create(this)
        FirebaseMessaging.getInstance().subscribeToTopic("my-topic")
    }
}