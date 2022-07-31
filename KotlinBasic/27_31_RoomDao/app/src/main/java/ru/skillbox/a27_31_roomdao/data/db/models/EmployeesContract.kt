package ru.skillbox.a27_31_roomdao.data.db.models

object EmployeesContract {
    const val TABLE_NAME = "employees"

    object Columns {
        const val ID = "id"
        const val COMPANY_ID = "company_id"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"
        const val BIRTHDATE = "birthdate"
        const val STATUS = "status"
    }
}