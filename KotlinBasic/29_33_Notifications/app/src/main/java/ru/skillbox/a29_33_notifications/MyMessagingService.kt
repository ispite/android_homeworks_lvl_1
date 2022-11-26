package ru.skillbox.a29_33_notifications

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.skillbox.a29_33_notifications.NotificationChannels.PROMO_NOTIFICATION_ID
import ru.skillbox.a29_33_notifications.presentation.MainActivity
import timber.log.Timber

class MyMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        when (message.data["type"]) {
            "promo" -> {
                val title = message.data["title"]
                val description = message.data["description"]
                val imageUrl = message.data["imageUrl"]
                if (title != null && description != null)
                    showPromoNotification(title, description, imageUrl)
            }
            "message" -> {
                // сделал userId Int вместо Long, для использования в качестве номера уведомления
                val userId = message.data["userId"]?.toInt()
                val userName = message.data["userName"]
                val createdAt = message.data["createdAt"]?.toLong()
                val text = message.data["text"]
                Timber.d("userId=$userId userName=$userName createdAt=$createdAt")
                if (userId != null && userName != null && createdAt != null && text != null)
                    showMessageNotification(userId, userName, createdAt, text)
            }
        }
    }

    private fun showMessageNotification(
        userId: Int,
        userName: String,
        createdAt: Long,
        text: String
    ) {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 123, intent, 0)

        val notification = NotificationCompat.Builder(
            this,
            NotificationChannels.HIGH_PRIORITY_CHANNEL_ID
        )
            .setContentTitle(userName)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(this)
            .notify(userId, notification)
    }

    private fun showPromoNotification(title: String, description: String, imageUrl: String?) {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 123, intent, 0)

        val bitmap = imageUrl?.let { getBitmapOrNull(it) }

        val notification = NotificationCompat.Builder(
            this,
            NotificationChannels.LOW_PRIORITY_CHANNEL_ID
        )
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(R.drawable.ic_notification)
            .setLargeIcon(bitmap)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(this)
            .notify(PROMO_NOTIFICATION_ID, notification)
    }

    private fun getBitmapOrNull(imageUrl: String): Bitmap? {
        if (imageUrl.isEmpty()) return null
        val futureTarget =
            try {
                Glide.with(this).asBitmap().load(imageUrl).submit().get()
            } catch (e: Exception) {
                Timber.e("Exception when load image: $e")
                throw e
            }
        return futureTarget
    }
}