package ru.skillbox.a27_31_roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = EmployeesContract.TABLE_NAME)
data class Employee(
    @PrimaryKey
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
