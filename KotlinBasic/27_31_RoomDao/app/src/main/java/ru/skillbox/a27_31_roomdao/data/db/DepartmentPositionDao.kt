package ru.skillbox.a27_31_roomdao.data.db

import androidx.room.*
import ru.skillbox.a27_31_roomdao.data.db.models.*

@Dao
interface DepartmentPositionDao {

    @Query("SELECT * FROM ${DepartmentPositionsContract.TABLE_NAME}")
    suspend fun getAllDepartmentPositions(): List<DepartmentPosition>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDepartmentPosition(departmentPosition: List<DepartmentPosition>)

    @Query("SELECT * FROM ${DepartmentPositionsContract.TABLE_NAME} WHERE ${DepartmentPositionsContract.Columns.ID} = :departmentPositionId")
    suspend fun getDepartmentPositionWithAllEmployees(departmentPositionId: Long):List<DepartmentPositionWithRelations>

    @Query("SELECT * FROM ${DepartmentPositionsContract.TABLE_NAME} WHERE ${DepartmentPositionsContract.Columns.ID} = :departmentPositionId")
    suspend fun getEmployeesWithDepartmentPositions(departmentPositionId: Long):List<EmployeesWithDepartmentPositions>

/*    @Query("SELECT * FROM ${DepartmentPositionsContract.TABLE_NAME} WHERE ${DepartmentPositionsContract.Columns.ID} = :departmentPositionId")
    suspend fun getDepartmentPositionWithEmployees(departmentPositionId: Long):List<DepartmentPositionWithEmployees>*/

//    @Query("SELECT * FROM ${DepartmentPositionsContract.TABLE_NAME} WHERE ${DepartmentPositionsContract.Columns.ID} = :departmentPositionId")
//    suspend fun getAnotherTry(departmentPositionId: Long):List<EmployeesDepartmentPositionsNew>

    @Transaction //??
    @Query("SELECT * FROM ${DepartmentPositionsContract.TABLE_NAME} WHERE ${DepartmentPositionsContract.Columns.ID} = :departmentPositionId")
    suspend fun getDepartmentWithEmployees(departmentPositionId: Long): List<DepartmentWithEmployees>

    @Query("DELETE FROM ${DepartmentPositionsContract.TABLE_NAME} WHERE ${DepartmentPositionsContract.Columns.ID} = :departmentPositionId")
    suspend fun removeDepartmentPosition(departmentPositionId: Long)

    @Query("SELECT * FROM ${DepartmentPositionsContract.TABLE_NAME} WHERE ${DepartmentPositionsContract.Columns.WORK_DEPARTMENT_ID} = :workDepartmentId")
    suspend fun getPositionsByWorkDepartmentId(workDepartmentId: Long): List<DepartmentPosition>

    @Query("SELECT * FROM ${DepartmentPositionsContract.TABLE_NAME} WHERE ${DepartmentPositionsContract.Columns.ID} = :departmentPositionId")
    suspend fun getDepartmentPositionById(departmentPositionId: Long): List<DepartmentPosition>
}