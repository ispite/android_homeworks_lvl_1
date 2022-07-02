package ru.skillbox.a27_31_roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = DepartmentPositionsContract.TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = WorkDepartment::class,
        parentColumns = [WorkDepartmentsContract.Columns.ID],
        childColumns = [DepartmentPositionsContract.Columns.WORK_DEPARTMENT_ID]
    )]
)
data class DepartmentPosition(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DepartmentPositionsContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = DepartmentPositionsContract.Columns.WORK_DEPARTMENT_ID)
    val workDepartmentId: Long,
    @ColumnInfo(name = DepartmentPositionsContract.Columns.JOB_TITLE)
    val jobTitle: String,
    @ColumnInfo(name = DepartmentPositionsContract.Columns.SALARY)
    val salary: Double
)
