package ru.skillbox.a27_31_roomdao.data.db.models

import androidx.room.*

@Entity(
    tableName = DepartmentPositionsContract.TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = WorkDepartment::class,
        parentColumns = [WorkDepartmentsContract.Columns.ID],
        childColumns = [DepartmentPositionsContract.Columns.WORK_DEPARTMENT_ID]
    )],
    indices = [Index(
        value = [DepartmentPositionsContract.Columns.JOB_TITLE, DepartmentPositionsContract.Columns.SALARY],
        unique = true
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
