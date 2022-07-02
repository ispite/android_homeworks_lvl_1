package ru.skillbox.a27_31_roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = WorkDepartmentsContract.TABLE_NAME)
data class WorkDepartment(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WorkDepartmentsContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = WorkDepartmentsContract.Columns.COMPANY_ID)
    val companyId: Long,
    @ColumnInfo(name = WorkDepartmentsContract.Columns.WORK_DEPARTMENT_NAME)
    val workDepartmentName: String
)
