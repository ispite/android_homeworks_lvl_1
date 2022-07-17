package ru.skillbox.a27_31_roomdao.data

import ru.skillbox.a27_31_roomdao.data.db.Database
import ru.skillbox.a27_31_roomdao.data.db.models.WorkDepartment

class WorkDepartmentRepository {

    private val workDepartmentDao = Database.instance.workDepartmentDao()

    suspend fun getAllWorkDepartments(): List<WorkDepartment> {
        return workDepartmentDao.getAllWorkDepartments()
    }
}