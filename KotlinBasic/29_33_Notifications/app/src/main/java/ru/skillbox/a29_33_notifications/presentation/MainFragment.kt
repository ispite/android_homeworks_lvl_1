package ru.skillbox.a29_33_notifications.presentation

import android.media.RingtoneManager
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.skillbox.a29_33_notifications.NotificationChannels
import ru.skillbox.a29_33_notifications.R
import ru.skillbox.a29_33_notifications.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sendSimpleNotification.setOnClickListener { showSimpleNotification() }
        binding.sendPriorityNotification.setOnClickListener { showPriorityNotification() }
    }

    private fun showSimpleNotification() {
        val notification = NotificationCompat.Builder(
            requireContext(),
            NotificationChannels.LOW_PRIORITY_CHANNEL_ID
        )
            .setContentTitle("Мой заголовок")
            .setContentText("Это простое оповещение с низким приоритетом")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(R.drawable.ic_notification)
            .build()

        NotificationManagerCompat.from(requireContext())
            .notify(SIMPLE_NOTIFICATION_ID, notification)
    }

    private fun showPriorityNotification() {
        val notification = NotificationCompat.Builder(
            requireContext(),
            NotificationChannels.HIGH_PRIORITY_CHANNEL_ID
        )
            .setContentTitle("Крайне выжный заголовок")
            .setContentText("Очень важное оповещение с высоким приоритетом")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setVibrate(longArrayOf(100, 200, 500, 500))
            .setSmallIcon(R.drawable.ic_priority_high)
            .build()

        NotificationManagerCompat.from(requireContext())
            .notify(HIGH_PRIORITY_NOTIFICATION_ID, notification)
    }

    companion object {
        private const val SIMPLE_NOTIFICATION_ID = 12341
        private const val HIGH_PRIORITY_NOTIFICATION_ID = 12342
    }
}