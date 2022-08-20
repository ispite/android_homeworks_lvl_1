package ru.skillbox.a27_31_roomdao.data

import kotlinx.coroutines.flow.Flow
import ru.skillbox.a27_31_roomdao.data.db.Database
import ru.skillbox.a27_31_roomdao.data.db.models.EmployeeDepartmentPosition

class EmployeeDepartmentPositionRepository {

    private val employeeDepartmentPositionDao = Database
        .instance.employeeDepartmentPositionDao()

    suspend fun getAllEmployeeDepartmentPosition(): List<EmployeeDepartmentPosition> {
        return employeeDepartmentPositionDao.getAllEmployeesDepartmentPosition()
    }

    suspend fun insertEmployeeDepartmentPosition(
        employeeDepartmentPosition: List<EmployeeDepartmentPosition>
    ) {
        employeeDepartmentPositionDao.insertEmployeeDepartmentPosition(employeeDepartmentPosition)
    }

    fun getEmployeeDepartmentPositionByDepartmentId(employeeDepartmentPositionId: Long):
            Flow<List<EmployeeDepartmentPosition>> {
        return employeeDepartmentPositionDao
            .getEmployeeDepartmentPositionByDepartmentId(employeeDepartmentPositionId)
    }
}