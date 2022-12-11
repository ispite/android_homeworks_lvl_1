package ru.skillbox.a29_33_notifications.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.skillbox.a29_33_notifications.data.db.models.MessageDb

@Database(entities = [MessageDb::class], version = MyDatabase.DB_VERSION)
abstract class MyDatabase : RoomDatabase() {

    abstract fun messageDao(): MessageDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "my_database"
    }
}