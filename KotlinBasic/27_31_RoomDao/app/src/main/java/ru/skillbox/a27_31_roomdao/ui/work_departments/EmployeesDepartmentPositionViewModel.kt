package ru.skillbox.a27_31_roomdao.ui.work_departments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.skillbox.a27_31_roomdao.data.DepartmentPositionRepository
import ru.skillbox.a27_31_roomdao.data.EmployeeDepartmentPositionRepository
import ru.skillbox.a27_31_roomdao.data.EmployeeRepository
import ru.skillbox.a27_31_roomdao.data.WorkDepartmentRepository
import ru.skillbox.a27_31_roomdao.data.db.models.EmployeeDepartmentPosition
import timber.log.Timber
import kotlin.random.Random

class EmployeesDepartmentPositionViewModel:ViewModel() {

    private val employeeDepartmentPositionRepository = EmployeeDepartmentPositionRepository()
    private val departmentPositionRepository = DepartmentPositionRepository()
    private val employeesRepository = EmployeeRepository()

    private val _employeesDepartmentPositionList = MutableLiveData<List<EmployeeDepartmentPosition>>()

    val employeesDepartmentPositionList: LiveData<List<EmployeeDepartmentPosition>>
        get() = _employeesDepartmentPositionList

    fun getAllEmployeesDepartmentPosition() {
        viewModelScope.launch {
            try {
                _employeesDepartmentPositionList.postValue(employeeDepartmentPositionRepository.getAllEmployeeDepartmentPosition())
            } catch (t: Throwable) {
                Timber.e(t, "employeesDepartmentPositionList error")
            }
        }
    }

    fun makeRelationsBetweenEmployeeAndDepartmentPositions() {
        viewModelScope.launch {
            val employees = employeesRepository.getAllEmployees()
            val departmentPositions = departmentPositionRepository.getAllDepartmentPositions()
//            var employeesDepartmentPosition: MutableList<EmployeeDepartmentPosition>? = null
/*            for (employee in employees) {
                val randomDepartmentPosition = (departmentPositions.indices).random()
                employeesDepartmentPosition?.add(EmployeeDepartmentPosition(0, employee.id, departmentPositions[randomDepartmentPosition].id))
            }*/
            val employeesDepartmentPosition = employees.map{ employee ->
                val randomDepartmentPosition = (departmentPositions.indices).random()
                EmployeeDepartmentPosition(0, employee.id, departmentPositions[randomDepartmentPosition].id)
            }
            employeeDepartmentPositionRepository.insertEmployeeDepartmentPosition(employeesDepartmentPosition)
        }
    }
}