package ru.skillbox.a27_31_roomdao.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.skillbox.a27_31_roomdao.data.db.models.DepartmentPosition
import ru.skillbox.a27_31_roomdao.data.db.models.DepartmentPositionsContract

@Dao
interface DepartmentPositionDao {

    @Query("SELECT * FROM ${DepartmentPositionsContract.TABLE_NAME}")
    suspend fun getAllDepartmentPositions(): List<DepartmentPosition>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDepartmentPosition(departmentPosition: List<DepartmentPosition>)

    @Query("DELETE FROM ${DepartmentPositionsContract.TABLE_NAME} WHERE ${DepartmentPositionsContract.Columns.ID} = :departmentPositionId")
    suspend fun removeDepartmentPosition(departmentPositionId: Long)

    @Query("SELECT * FROM ${DepartmentPositionsContract.TABLE_NAME} WHERE ${DepartmentPositionsContract.Columns.WORK_DEPARTMENT_ID} = :workDepartmentId")
    suspend fun getPositionsByWorkDepartmentId(workDepartmentId: Long): List<DepartmentPosition>

    @Query("SELECT * FROM ${DepartmentPositionsContract.TABLE_NAME} WHERE ${DepartmentPositionsContract.Columns.ID} = :departmentPositionId")
    suspend fun getDepartmentPositionById(departmentPositionId: Long): List<DepartmentPosition>
}