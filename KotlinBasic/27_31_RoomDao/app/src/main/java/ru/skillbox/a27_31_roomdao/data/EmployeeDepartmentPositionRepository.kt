package ru.skillbox.a27_31_roomdao.data

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

    suspend fun getEmployeeDepartmentPositionByDepartmentId(employeeDepartmentPositionId: Long):
            List<EmployeeDepartmentPosition> {
        return employeeDepartmentPositionDao
            .getEmployeeDepartmentPositionByDepartmentId(employeeDepartmentPositionId)
    }
}