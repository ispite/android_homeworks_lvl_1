package ru.skillbox.a27_31_roomdao.data

import ru.skillbox.a27_31_roomdao.data.db.Database
import ru.skillbox.a27_31_roomdao.data.db.models.Employee

class EmployeeRepository {

    private val employeeDao = Database.instance.employeeDao()

    suspend fun getAllEmployees(): List<Employee> {
        return employeeDao.getAllEmployees()
    }

    suspend fun insertEmployee(employee: Employee) {
        if (isEmployeeValid(employee).not()) throw IncorrectFormException()
        employeeDao.insertEmployees(listOf(employee))
    }

    suspend fun updateEmployee(employee: Employee) {
        if (isEmployeeValid(employee).not()) throw IncorrectFormException()
        employeeDao.updateEmployee(employee)
    }

    suspend fun removeEmployeeById(employeeId: Long) {
        employeeDao.removeEmployeeById(employeeId)
    }

    private fun isEmployeeValid(employee: Employee): Boolean {
        return employee.firstName.isNotBlank() &&
                employee.lastName.isNotBlank() &&
                employee.birthdate.isNotBlank()
    }

    suspend fun getEmployeeById(employeeId: Long): Employee? {
        return employeeDao.getEmployeeById(employeeId)
    }
}