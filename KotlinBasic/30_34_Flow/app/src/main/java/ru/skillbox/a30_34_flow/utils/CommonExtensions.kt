package ru.skillbox.a30_34_flow.utils

import android.os.Build

fun haveP(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P // P версия = 9 android, API 28
}