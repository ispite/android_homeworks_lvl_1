package ru.skillbox.a27_31_roomdao.data.db.models

object DepartmentPositionsContract {
    const val TABLE_NAME = "department_positions"

    object Columns {
        const val ID = "id"
        const val WORK_DEPARTMENT_ID = "work_department_id"
        const val JOB_TITLE = "job_title"
        const val SALARY = "salary"
    }
}