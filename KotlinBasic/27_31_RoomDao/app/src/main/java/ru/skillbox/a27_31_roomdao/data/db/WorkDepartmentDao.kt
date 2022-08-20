package ru.skillbox.a27_31_roomdao.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.skillbox.a27_31_roomdao.data.db.models.WorkDepartment
import ru.skillbox.a27_31_roomdao.data.db.models.WorkDepartmentsContract

@Dao
interface WorkDepartmentDao {

    @Query("SELECT * FROM ${WorkDepartmentsContract.TABLE_NAME}")
    fun getAllWorkDepartments(): Flow<List<WorkDepartment>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWorkDepartment(workDepartment: List<WorkDepartment>)

    @Query("DELETE FROM ${WorkDepartmentsContract.TABLE_NAME} WHERE ${WorkDepartmentsContract.Columns.ID} = :workDepartmentId")
    suspend fun removeWorkDepartmentById(workDepartmentId: Long)
}