package ru.skillbox.a27_31_roomdao.data.db.models

object EmployeesDepartmentPositionContract {
    const val TABLE_NAME = "employees_department_positions"

    object Columns {
        const val ID = "id"
        const val EMPLOYEE_ID = "employee_id"
        const val DEPARTMENT_POSITION_ID = "department_position_id"
    }
}