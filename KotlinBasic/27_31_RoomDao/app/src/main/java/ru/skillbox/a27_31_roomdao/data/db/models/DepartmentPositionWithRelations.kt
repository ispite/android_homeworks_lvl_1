package ru.skillbox.a27_31_roomdao.data.db.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class DepartmentPositionWithRelations(
    @Embedded
    val departmentPosition: DepartmentPosition,
    @Relation(
        parentColumn = DepartmentPositionsContract.Columns.ID,
        entityColumn = EmployeesDepartmentPositionContract.Columns.DEPARTMENT_POSITION_ID
    )
    val employeesDepartmentPosition: List<EmployeeDepartmentPosition>,
)

data class DepartmentWithEmployees(
    @Embedded
    val departmentPosition: DepartmentPosition,
    @Relation(
        parentColumn = DepartmentPositionsContract.Columns.ID,
        entity = Employee::class,
        entityColumn = EmployeesContract.Columns.ID,
        associateBy = Junction(
            value = EmployeeDepartmentPosition::class,
            parentColumn = EmployeesDepartmentPositionContract.Columns.DEPARTMENT_POSITION_ID,
            entityColumn = EmployeesDepartmentPositionContract.Columns.EMPLOYEE_ID
        )
    )
    val employees: List<Employee>
)

data class EmployeesWithDepartmentPositions(
    @Embedded
    val departmentPosition: DepartmentPosition,
    @Relation(
        parentColumn = DepartmentPositionsContract.Columns.ID,
        entityColumn = EmployeesDepartmentPositionContract.Columns.DEPARTMENT_POSITION_ID,
    )
    val employees: List<EmployeeDepartmentPosition>
)