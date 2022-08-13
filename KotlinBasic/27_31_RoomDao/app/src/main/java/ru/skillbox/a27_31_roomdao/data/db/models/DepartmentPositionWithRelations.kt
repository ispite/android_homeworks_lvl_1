package ru.skillbox.a27_31_roomdao.data.db.models

import androidx.room.*

data class DepartmentPositionWithRelations(
    @Embedded
    val departmentPosition: DepartmentPosition,
    @Relation(
        parentColumn = DepartmentPositionsContract.Columns.ID,
        entityColumn = EmployeesDepartmentPositionContract.Columns.DEPARTMENT_POSITION_ID
    )
    val employeesDepartmentPosition: List<EmployeeDepartmentPosition>,
)

/*data class EmployeesDepartmentPositionsNew(
    @Embedded
    val employeesDepartmentPosition: EmployeeDepartmentPosition,
    @Relation(
        parentColumn = EmployeesDepartmentPositionContract.Columns.EMPLOYEE_ID,
        entityColumn = EmployeesContract.Columns.ID
    )
    val employees: List<DepartmentPositionWithRelations>
)*/

/*data class EmployeesWithRelations(
    @Embedded
    val employee: Employee,
    @Relation(
        parentColumn = EmployeesContract.Columns.ID,
        entityColumn = EmployeesDepartmentPositionContract.Columns.EMPLOYEE_ID
    )
    val employeesWithRelations: List<DepartmentPositionWithRelations>
)*/
//        parentColumn = EmployeesDepartmentPositionContract.Columns.DEPARTMENT_POSITION_ID,
//        entityColumn = EmployeesDepartmentPositionContract.Columns.EMPLOYEE_ID,

/*@Entity(
    primaryKeys = [EmployeesDepartmentPositionContract.Columns.EMPLOYEE_ID,
        EmployeesDepartmentPositionContract.Columns.DEPARTMENT_POSITION_ID]
)
data class EmployeesDepartmentPositionCrossRef(
    @ColumnInfo(name = EmployeesDepartmentPositionContract.Columns.EMPLOYEE_ID)
    val employeesId: Long,
    @ColumnInfo(name = EmployeesDepartmentPositionContract.Columns.DEPARTMENT_POSITION_ID)
    val departmentPositionId: Long
)

data class DepartmentPositionWithEmployees(
    @Embedded
    val departmentPosition: DepartmentPosition,
    @Relation(
        parentColumn = DepartmentPositionsContract.Columns.ID,
        entityColumn = EmployeesContract.Columns.ID,
        associateBy = Junction(EmployeesDepartmentPositionCrossRef::class)
    )
    val employees: List<Employee>
)*/

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
//        associateBy = Junction(EmployeesDepartmentPositionCrossRef::class)
//        associateBy = Junction(EmployeeDepartmentPosition::class)
    )
    val employees: List<EmployeeDepartmentPosition>
)