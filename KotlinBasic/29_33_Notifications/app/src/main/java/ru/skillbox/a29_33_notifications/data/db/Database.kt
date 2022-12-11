package ru.skillbox.a29_33_notifications.data.db

import android.content.Context
import androidx.room.Room

object Database {

    lateinit var instance: MyDatabase
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(context, MyDatabase::class.java, MyDatabase.DB_NAME)
            .build()
    }
}