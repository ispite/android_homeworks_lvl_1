package ru.skillbox.a29_33_notifications.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

fun <T : Fragment> T.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}