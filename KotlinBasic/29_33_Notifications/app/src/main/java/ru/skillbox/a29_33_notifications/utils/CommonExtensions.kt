package ru.skillbox.a29_33_notifications.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

fun <T : Fragment> T.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun <T : ViewBinding> ViewGroup.inflate(
    inflateBinding: (
        inflater: LayoutInflater,
        root: ViewGroup?,
        attachToRoot: Boolean
    ) -> T,
    attachToRoot: Boolean = false
): T {
    val inflater = LayoutInflater.from(context)
    return inflateBinding(inflater, this, attachToRoot)
}