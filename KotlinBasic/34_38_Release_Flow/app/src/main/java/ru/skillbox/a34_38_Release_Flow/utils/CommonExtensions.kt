package ru.skillbox.a34_38_Release_Flow.utils

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

fun haveP(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P // P версия = 9 android, API 28
}

fun <T : ViewBinding> ViewGroup.inflate(
    inflateBinding: (
        inflater: LayoutInflater,
        root: ViewGroup?,
        attachToRoot: Boolean
    ) -> T, attachToRoot: Boolean = false
): T {
    val inflater = LayoutInflater.from(context)
    return inflateBinding(inflater, this, attachToRoot)
}