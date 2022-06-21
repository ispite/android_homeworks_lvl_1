package ru.skillbox.a27_31_roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = EmployeesContract.TABLE_NAME,
    indices = [Index(
        value = [EmployeesContract.Columns.FIRST_NAME, EmployeesContract.Columns.LAST_NAME, EmployeesContract.Columns.BIRTHDATE],
        unique = true
    )]
)
data class Employee(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = EmployeesContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = EmployeesContract.Columns.COMPANY_ID)
    val companyId: Long,
    @ColumnInfo(name = EmployeesContract.Columns.FIRST_NAME)
    val firstName: String,
    @ColumnInfo(name = EmployeesContract.Columns.LAST_NAME)
    val lastName: String,
    @ColumnInfo(name = EmployeesContract.Columns.BIRTHDATE)
    val birthdate: String
)
