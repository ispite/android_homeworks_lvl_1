package ru.skillbox.a27_31_roomdao.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.skillbox.a27_31_roomdao.data.db.models.Employee

@Database(entities = [Employee::class], version = MyDatabase.DB_VERSION)
abstract class MyDatabase: RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "my-database"
    }
}