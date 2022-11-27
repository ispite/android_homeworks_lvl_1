package ru.skillbox.a29_33_notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

object NotificationChannels {

    // Channels
    const val HIGH_PRIORITY_CHANNEL_ID = "high_priority"
    const val LOW_PRIORITY_CHANNEL_ID = "low_priority"

    // Notification ID's
    const val SIMPLE_NOTIFICATION_ID = 12341
    const val HIGH_PRIORITY_NOTIFICATION_ID = 12342
    const val PROMO_NOTIFICATION_ID = 12343
    const val SYNCHRONIZATION_NOTIFICATION_ID = 12344

    fun create(context: Context) {
        // O версия = 8 android, API 26
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createHighPriorityChannel(context)
            createLowPriorityChannel(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createHighPriorityChannel(context: Context) {
        val name = "Очень важный канал"
        val channelDescription = "Очень важный канал для крайне важных оповещений"
        val priority = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(HIGH_PRIORITY_CHANNEL_ID, name, priority).apply {
            description = channelDescription
            enableVibration(true)
            vibrationPattern = longArrayOf(100, 200, 500, 500)
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
        }
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createLowPriorityChannel(context: Context) {
        val name = "Не важный канал"
        val channelDescription = "Канал для оповещений"
        val priority = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(LOW_PRIORITY_CHANNEL_ID, name, priority).apply {
            description = channelDescription
        }
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }
}