package ru.skillbox.a34_38_Release_Flow.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.skillbox.a34_38_Release_Flow.data.db.dao.MovieDao
import ru.skillbox.a34_38_Release_Flow.data.db.models.MovieDB

@Database(
    entities = [MovieDB::class],
    version = MyDatabase.DB_VERSION
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "my-database"
    }
}