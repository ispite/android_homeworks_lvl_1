package ru.skillbox.a27_31_roomdao.data

import kotlinx.coroutines.flow.Flow
import ru.skillbox.a27_31_roomdao.data.db.Database
import ru.skillbox.a27_31_roomdao.data.db.models.DepartmentPosition
import ru.skillbox.a27_31_roomdao.data.db.models.DepartmentPositionWithRelations
import ru.skillbox.a27_31_roomdao.data.db.models.DepartmentWithEmployees
import ru.skillbox.a27_31_roomdao.data.db.models.EmployeesWithDepartmentPositions

class DepartmentPositionRepository {
    private val departmentPosition = Database.instance.departmentPositionDao()

    suspend fun getAllDepartmentPositions(): List<DepartmentPosition> {
        return departmentPosition.getAllDepartmentPositions()
    }

    fun getPositionsByWorkDepartmentId(workDepartmentId: Long): Flow<List<DepartmentPosition>> {
        return departmentPosition.getPositionsByWorkDepartmentId(workDepartmentId)
    }

    suspend fun getDepartmentPositionById(departmentPositionId: Long): List<DepartmentPosition> {
        return departmentPosition.getDepartmentPositionById(departmentPositionId)
    }

    fun getDepartmentPositionWithAllEmployees(departmentPositionId: Long): Flow<List<DepartmentPositionWithRelations>> {
        return departmentPosition.getDepartmentPositionWithAllEmployees(departmentPositionId)
    }

    suspend fun getEmployeesWithDepartmentPositions(departmentPositionId: Long): List<EmployeesWithDepartmentPositions> {
        return departmentPosition.getEmployeesWithDepartmentPositions(departmentPositionId)
    }

    suspend fun getDepartmentWithEmployees(departmentPositionId: Long): List<DepartmentWithEmployees> {
        return departmentPosition.getDepartmentWithEmployees(departmentPositionId)
    }
}