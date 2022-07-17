package ru.skillbox.a27_31_roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = WorkDepartmentsContract.TABLE_NAME,
    indices = [Index(
        value = [WorkDepartmentsContract.Columns.WORK_DEPARTMENT_NAME],
        unique = true
    )]
)
data class WorkDepartment(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WorkDepartmentsContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = WorkDepartmentsContract.Columns.COMPANY_ID)
    val companyId: Long,
    @ColumnInfo(name = WorkDepartmentsContract.Columns.WORK_DEPARTMENT_NAME)
    val workDepartmentName: String
)
