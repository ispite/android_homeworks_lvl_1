package ru.skillbox.a27_31_roomdao.data.db

import androidx.room.Dao
import androidx.room.Query
import ru.skillbox.a27_31_roomdao.data.db.models.Employee
import ru.skillbox.a27_31_roomdao.data.db.models.EmployeesContract

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM ${EmployeesContract.TABLE_NAME}")
    fun getAllEmployees(): List<Employee>

}