package ru.skillbox.a27_31_roomdao.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.skillbox.a27_31_roomdao.data.db.models.Employee
import ru.skillbox.a27_31_roomdao.data.db.models.EmployeesContract

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM ${EmployeesContract.TABLE_NAME}")
    /*suspend*/ fun getAllEmployees(): Flow<List<Employee>>

    @Insert
    suspend fun insertEmployees(employees: List<Employee>)

    @Delete
    suspend fun removeEmployee(employee: Employee)

    @Query("DELETE FROM ${EmployeesContract.TABLE_NAME} WHERE ${EmployeesContract.Columns.ID} = :employeeId")
    suspend fun removeEmployeeById(employeeId: Long)

    @Query("SELECT * FROM ${EmployeesContract.TABLE_NAME} WHERE ${EmployeesContract.Columns.ID} = :employeeId")
    suspend fun getEmployeeById(employeeId: Long): Employee?

    @Update
    suspend fun updateEmployee(employee: Employee)
}