package ru.skillbox.a27_31_roomdao.data

import ru.skillbox.a27_31_roomdao.data.db.Database
import ru.skillbox.a27_31_roomdao.data.db.models.DepartmentPosition
import ru.skillbox.a27_31_roomdao.data.db.models.DepartmentPositionWithRelations
//import ru.skillbox.a27_31_roomdao.data.db.models.EmployeesDepartmentPositionsNew
import ru.skillbox.a27_31_roomdao.data.db.models.EmployeesWithDepartmentPositions

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

    suspend fun getDepartmentPositionWithAllEmployees(departmentPositionId: Long): List<DepartmentPositionWithRelations> {
        return departmentPosition.getDepartmentPositionWithAllEmployees(departmentPositionId)
    }

    suspend fun getEmployeesWithDepartmentPositions(departmentPositionId: Long): List<EmployeesWithDepartmentPositions> {
        return departmentPosition.getEmployeesWithDepartmentPositions(departmentPositionId)
    }

/*    suspend fun getAnotherTry(departmentPositionId: Long): List<EmployeesDepartmentPositionsNew> {
        return departmentPosition.getAnotherTry(departmentPositionId)
    }*/
}