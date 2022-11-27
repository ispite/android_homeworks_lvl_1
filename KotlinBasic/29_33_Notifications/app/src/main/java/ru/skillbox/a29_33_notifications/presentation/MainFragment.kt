package ru.skillbox.a29_33_notifications.presentation

import android.media.RingtoneManager
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import ru.skillbox.a29_33_notifications.NotificationChannels
import ru.skillbox.a29_33_notifications.NotificationChannels.HIGH_PRIORITY_NOTIFICATION_ID
import ru.skillbox.a29_33_notifications.NotificationChannels.SIMPLE_NOTIFICATION_ID
import ru.skillbox.a29_33_notifications.R
import ru.skillbox.a29_33_notifications.databinding.FragmentMainBinding
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sendSimpleNotification.setOnClickListener { showSimpleNotification() }
        binding.sendPriorityNotification.setOnClickListener { showPriorityNotification() }
        binding.getFirebaseToken.setOnClickListener { getToken() }
        binding.toSynchronizationFragmentButton.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_synchronizationFragment) }
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

    private fun getToken() {
        lifecycleScope.launch {
            val token = getTokenSuspend()
            Timber.d("token=$token")
        }
    }

    private suspend fun getTokenSuspend(): String? = suspendCoroutine { continuation ->
        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { token ->
                continuation.resume(token)
            }
            .addOnFailureListener { exception ->
                continuation.resume(null)
                Timber.e(exception)
            }
            .addOnCanceledListener {
                continuation.resume(null)
            }
    }
}