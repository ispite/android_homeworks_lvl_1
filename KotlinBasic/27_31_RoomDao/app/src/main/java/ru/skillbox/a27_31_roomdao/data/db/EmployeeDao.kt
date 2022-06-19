package ru.skillbox.a27_31_roomdao.data.db

import androidx.room.*
import ru.skillbox.a27_31_roomdao.data.db.models.Employee
import ru.skillbox.a27_31_roomdao.data.db.models.EmployeesContract

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM ${EmployeesContract.TABLE_NAME}")
    suspend fun getAllEmployees(): List<Employee>

    @Insert
    suspend fun insertEmployees(employees: List<Employee>)

    @Update
    suspend fun updateUser(employee: Employee)

    @Delete
    suspend fun removeEmployee(employee: Employee)

    @Query("DELETE FROM ${EmployeesContract.TABLE_NAME} WHERE ${EmployeesContract.Columns.ID} = :employeeId")
    suspend fun removeEmployeeById(employeeId: Long)
}