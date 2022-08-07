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

//@Entity(primaryKeys = [EmployeesContract.Columns.ID, DepartmentPositionsContract.Columns.WORK_DEPARTMENT_ID])
/*data class EmployeesDepartmentPositionCrossRef(
//    @ColumnInfo(name = EmployeesContract.Columns.ID)
    val employeesId: Long,
//    @ColumnInfo(name = DepartmentPositionsContract.Columns.WORK_DEPARTMENT_ID)
    val departmentPositionId: Long
)*/

data class EmployeesWithDepartmentPositions(
    @Embedded
    val departmentPosition: DepartmentPosition,
    @Relation(
//        entity = EmployeeDepartmentPosition::class,
        parentColumn = DepartmentPositionsContract.Columns.ID,
        entityColumn = EmployeesDepartmentPositionContract.Columns.DEPARTMENT_POSITION_ID,
//        associateBy = Junction(EmployeesDepartmentPositionCrossRef::class)
//        associateBy = Junction(EmployeeDepartmentPosition::class)
    )
    val employees: List<EmployeeDepartmentPosition>
)