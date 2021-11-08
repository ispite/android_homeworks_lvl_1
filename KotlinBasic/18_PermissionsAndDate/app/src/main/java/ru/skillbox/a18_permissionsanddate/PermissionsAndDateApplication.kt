package ru.skillbox.a18_permissionsanddate

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class PermissionsAndDateApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}