package ru.skillbox.a27_31_roomdao.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = EmployeesDepartmentPositionContract.TABLE_NAME,
    foreignKeys = [ForeignKey(
        entity = Employee::class,
        parentColumns = [EmployeesContract.Columns.ID],
        childColumns = [EmployeesDepartmentPositionContract.Columns.EMPLOYEE_ID]
    ),
        ForeignKey(
            entity = DepartmentPosition::class,
            parentColumns = [DepartmentPositionsContract.Columns.ID],
            childColumns = [EmployeesDepartmentPositionContract.Columns.DEPARTMENT_POSITION_ID]
        )
    ]
)
data class EmployeeDepartmentPosition(
    @PrimaryKey
    @ColumnInfo(name = EmployeesDepartmentPositionContract.Columns.ID)
    val id: Long,
    @ColumnInfo(name = EmployeesDepartmentPositionContract.Columns.EMPLOYEE_ID)
    val employee_id: Long,
    @ColumnInfo(name = EmployeesDepartmentPositionContract.Columns.DEPARTMENT_POSITION_ID)
    val departmentPosition_id: Long
)
