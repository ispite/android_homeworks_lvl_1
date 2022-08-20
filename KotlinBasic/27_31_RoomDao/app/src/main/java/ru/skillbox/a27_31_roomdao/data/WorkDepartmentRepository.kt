package ru.skillbox.a27_31_roomdao.data

import kotlinx.coroutines.flow.Flow
import ru.skillbox.a27_31_roomdao.data.db.Database
import ru.skillbox.a27_31_roomdao.data.db.models.WorkDepartment

class WorkDepartmentRepository {

    private val workDepartmentDao = Database.instance.workDepartmentDao()

    fun getAllWorkDepartments(): Flow<List<WorkDepartment>> {
        return workDepartmentDao.getAllWorkDepartments()
    }
}