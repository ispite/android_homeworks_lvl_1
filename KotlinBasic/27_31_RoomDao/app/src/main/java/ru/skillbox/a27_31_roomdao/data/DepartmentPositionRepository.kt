package ru.skillbox.a27_31_roomdao.data

import ru.skillbox.a27_31_roomdao.data.db.Database
import ru.skillbox.a27_31_roomdao.data.db.models.DepartmentPosition

class DepartmentPositionRepository {
    private val departmentPosition = Database.instance.departmentPositionDao()

    suspend fun getAllDepartmentPositions(): List<DepartmentPosition> {
        return departmentPosition.getAllDepartmentPositions()
    }

    suspend fun getPositionsByWorkDepartmentId(workDepartmentId: Long): List<DepartmentPosition> {
        return departmentPosition.getPositionsByWorkDepartmentId(workDepartmentId)
    }

    suspend fun getDepartmentPositionById(departmentPositionId: Long): List<DepartmentPosition> {
        return departmentPosition.getDepartmentPositionById(departmentPositionId)
    }
}