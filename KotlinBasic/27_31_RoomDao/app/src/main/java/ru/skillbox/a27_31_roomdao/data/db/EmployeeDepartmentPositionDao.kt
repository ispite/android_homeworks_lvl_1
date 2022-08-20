package ru.skillbox.a27_31_roomdao.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.skillbox.a27_31_roomdao.data.db.models.EmployeeDepartmentPosition
import ru.skillbox.a27_31_roomdao.data.db.models.EmployeesDepartmentPositionContract

@Dao
interface EmployeeDepartmentPositionDao {

    @Query("SELECT * FROM ${EmployeesDepartmentPositionContract.TABLE_NAME}")
    suspend fun getAllEmployeesDepartmentPosition(): List<EmployeeDepartmentPosition>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEmployeeDepartmentPosition(employeeDepartmentPosition: List<EmployeeDepartmentPosition>)

    @Query("SELECT * FROM ${EmployeesDepartmentPositionContract.TABLE_NAME} WHERE ${EmployeesDepartmentPositionContract.Columns.DEPARTMENT_POSITION_ID} = :employeeDepartmentPositionId")
    fun getEmployeeDepartmentPositionByDepartmentId(employeeDepartmentPositionId: Long): Flow<List<EmployeeDepartmentPosition>>
}