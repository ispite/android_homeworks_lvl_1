package ru.skillbox.a29_33_notifications.presentation

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.skillbox.a29_33_notifications.NetworkBroadcastReceiver
import ru.skillbox.a29_33_notifications.R
import ru.skillbox.a29_33_notifications.databinding.FragmentSynchronizationBinding
import ru.skillbox.a29_33_notifications.utils.toast
import timber.log.Timber

class SynchronizationFragment : Fragment(R.layout.fragment_synchronization) {

    private val viewModel: SynchronizationViewModel by viewModels()
    private val networkReceiver = NetworkBroadcastReceiver()
    private val binding: FragmentSynchronizationBinding by viewBinding(
        FragmentSynchronizationBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.synchronizationButton.setOnClickListener { updateConnectedFlags() }
        bindViewModel()
    }

    override fun onResume() {
        super.onResume()
        requireContext().registerReceiver(
            networkReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onPause() {
        super.onPause()
        requireContext().unregisterReceiver(networkReceiver)
    }

    private fun bindViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.synchronizationButton.isEnabled = !it
        }
    }

    // Checks the network connection and sets the wifiConnected and mobileConnected
    // variables accordingly.
    private fun updateConnectedFlags() {
        val connMgr =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeInfo: NetworkInfo? = connMgr.activeNetworkInfo
        if (activeInfo?.isConnected == true) {
            wifiConnected = activeInfo.type == ConnectivityManager.TYPE_WIFI
            mobileConnected = activeInfo.type == ConnectivityManager.TYPE_MOBILE
        } else {
            wifiConnected = false
            mobileConnected = false
        }
        Timber.d("wifiConnected=$wifiConnected mobileConnected=$mobileConnected")
        if (!wifiConnected && !wifiConnected) {
            toast("Подключитесь к интернету")
        } else {
            viewModel.showProgressNotification()
        }
    }

    companion object {
        // Whether there is a Wi-Fi connection.
        private var wifiConnected = false

        // Whether there is a mobile connection.
        private var mobileConnected = false
    }
}