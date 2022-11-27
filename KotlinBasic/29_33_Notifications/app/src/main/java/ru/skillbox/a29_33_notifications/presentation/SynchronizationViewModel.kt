package ru.skillbox.a29_33_notifications.presentation

import android.app.Application
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.skillbox.a29_33_notifications.NotificationChannels
import ru.skillbox.a29_33_notifications.R

class SynchronizationViewModel(val app: Application) : AndroidViewModel(app) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun showProgressNotification() {
        val notificationBuilder = NotificationCompat.Builder(
            app, NotificationChannels.LOW_PRIORITY_CHANNEL_ID
        )
            .setContentTitle("Синхронизация")
            .setContentText("Синхронизация в процессе")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(R.drawable.ic_sync)

        val maxProgress = 10
        viewModelScope.launch {
            _isLoading.postValue(true)
            (0 until maxProgress).forEach { progress ->
                val notification = notificationBuilder
                    .setProgress(maxProgress, progress, false)
                    .build()

                NotificationManagerCompat.from(app)
                    .notify(NotificationChannels.SYNCHRONIZATION_NOTIFICATION_ID, notification)

                delay(500)
            }

            val finalNotification = notificationBuilder
                .setContentText("Синхронизация завершена")
                .setProgress(0, 0, false)
                .build()

            NotificationManagerCompat.from(app)
                .notify(NotificationChannels.SYNCHRONIZATION_NOTIFICATION_ID, finalNotification)
            delay(1000)

            _isLoading.postValue(false)
            NotificationManagerCompat.from(app)
                .cancel(NotificationChannels.SYNCHRONIZATION_NOTIFICATION_ID)
        }
    }
}