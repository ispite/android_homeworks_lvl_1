package ru.skillbox.a29_33_notifications.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import ru.skillbox.a29_33_notifications.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onResume() {
        super.onResume()
        NotificationManagerCompat.from(this).cancelAll()
    }
}