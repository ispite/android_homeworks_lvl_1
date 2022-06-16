package ru.skillbox.a27_31_roomdao.data.db

import android.content.Context
import androidx.room.Room

object Database {

    lateinit var instance: MyDatabase
        private set

    fun init(context: Context) {
        Room.databaseBuilder(context, MyDatabase::class.java, MyDatabase.DB_NAME)
    }
}