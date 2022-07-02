package ru.skillbox.a27_31_roomdao.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.skillbox.a27_31_roomdao.data.db.models.DepartmentPosition
import ru.skillbox.a27_31_roomdao.data.db.models.Employee
import ru.skillbox.a27_31_roomdao.data.db.models.WorkDepartment

@Database(
    entities = [Employee::class,
        WorkDepartment::class,
        DepartmentPosition::class], version = MyDatabase.DB_VERSION
)
abstract class MyDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao
    abstract fun workDepartmentDao(): WorkDepartmentDao
    abstract fun departmentPositionDao(): DepartmentPositionDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "my-database"
    }
}