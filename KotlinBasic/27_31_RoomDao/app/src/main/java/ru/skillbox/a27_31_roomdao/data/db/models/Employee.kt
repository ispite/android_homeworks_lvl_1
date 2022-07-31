package ru.skillbox.a27_31_roomdao.data.db.models

import androidx.room.*
import ru.skillbox.a27_31_roomdao.data.db.EmployeeStatusConverter

@Entity(
    tableName = EmployeesContract.TABLE_NAME,
    indices = [Index(
        value = [EmployeesContract.Columns.FIRST_NAME, EmployeesContract.Columns.LAST_NAME, EmployeesContract.Columns.BIRTHDATE],
        unique = true
    )]
)
@TypeConverters(EmployeeStatusConverter::class)
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
    val birthdate: String,
    @ColumnInfo(name = EmployeesContract.Columns.STATUS)
    val status: EmployeeStatus
)
