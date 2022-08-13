package ru.skillbox.a27_31_roomdao.data.db

import android.content.Context
import androidx.room.Room

object Database {

    lateinit var instance: MyDatabase
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(context, MyDatabase::class.java, MyDatabase.DB_NAME)
            .addMigrations(MIGRATION_1_2)
            .addMigrations(MIGRATION_2_3)
            .build()
    }
}