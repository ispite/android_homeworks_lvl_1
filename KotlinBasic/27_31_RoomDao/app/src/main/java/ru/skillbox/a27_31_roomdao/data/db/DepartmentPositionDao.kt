package ru.skillbox.a27_31_roomdao.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.skillbox.a27_31_roomdao.data.db.models.*

@Dao
interface DepartmentPositionDao {

    @Query("SELECT * FROM ${DepartmentPositionsContract.TABLE_NAME}")
    suspend fun getAllDepartmentPositions(): List<DepartmentPosition>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDepartmentPosition(departmentPosition: List<DepartmentPosition>)

    @Query("SELECT * FROM ${DepartmentPositionsContract.TABLE_NAME} WHERE ${DepartmentPositionsContract.Columns.ID} = :departmentPositionId")
    fun getDepartmentPositionWithAllEmployees(departmentPositionId: Long): Flow<List<DepartmentPositionWithRelations>>

    @Query("SELECT * FROM ${DepartmentPositionsContract.TABLE_NAME} WHERE ${DepartmentPositionsContract.Columns.ID} = :departmentPositionId")
    suspend fun getEmployeesWithDepartmentPositions(departmentPositionId: Long): List<EmployeesWithDepartmentPositions>

    @Transaction //??
    @Query("SELECT * FROM ${DepartmentPositionsContract.TABLE_NAME} WHERE ${DepartmentPositionsContract.Columns.ID} = :departmentPositionId")
    suspend fun getDepartmentWithEmployees(departmentPositionId: Long): List<DepartmentWithEmployees>

    @Query("DELETE FROM ${DepartmentPositionsContract.TABLE_NAME} WHERE ${DepartmentPositionsContract.Columns.ID} = :departmentPositionId")
    suspend fun removeDepartmentPosition(departmentPositionId: Long)

    @Query("SELECT * FROM ${DepartmentPositionsContract.TABLE_NAME} WHERE ${DepartmentPositionsContract.Columns.WORK_DEPARTMENT_ID} = :workDepartmentId")
    fun getPositionsByWorkDepartmentId(workDepartmentId: Long): Flow<List<DepartmentPosition>>

    @Query("SELECT * FROM ${DepartmentPositionsContract.TABLE_NAME} WHERE ${DepartmentPositionsContract.Columns.ID} = :departmentPositionId")
    suspend fun getDepartmentPositionById(departmentPositionId: Long): List<DepartmentPosition>
}