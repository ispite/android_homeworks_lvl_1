package ru.skillbox.a27_31_roomdao.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.skillbox.a27_31_roomdao.data.db.models.WorkDepartment
import ru.skillbox.a27_31_roomdao.data.db.models.WorkDepartmentsContract

@Dao
interface WorkDepartmentDao {

    @Query("SELECT * FROM ${WorkDepartmentsContract.TABLE_NAME}")
    suspend fun getAllWorkDepartments():List<WorkDepartment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkDepartment(workDepartment: List<WorkDepartment>)

    @Query("DELETE FROM ${WorkDepartmentsContract.TABLE_NAME} WHERE ${WorkDepartmentsContract.Columns.ID} = :workDepartmentId")
    suspend fun removeWorkDepartmentById(workDepartmentId: Long)
}