package ru.skillbox.a27_31_roomdao.ui.work_departments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.skillbox.a27_31_roomdao.data.DepartmentPositionRepository
import ru.skillbox.a27_31_roomdao.data.EmployeeDepartmentPositionRepository
import ru.skillbox.a27_31_roomdao.data.EmployeeRepository
import ru.skillbox.a27_31_roomdao.data.db.models.*
import timber.log.Timber

class EmployeesDepartmentPositionViewModel : ViewModel() {

    private val employeeDepartmentPositionRepository = EmployeeDepartmentPositionRepository()
    private val departmentPositionRepository = DepartmentPositionRepository()
    private val employeesRepository = EmployeeRepository()

    private val _employeesDepartmentPositionList =
        MutableLiveData<List<EmployeeDepartmentPosition>>()
    private val _departmentPosition = MutableLiveData<DepartmentPosition>()
    private val _employeePositionsListPair =
        MutableLiveData<List<Pair<EmployeeDepartmentPosition, Employee>>>()
    private val _departmentPositionWithRelations =
        MutableLiveData<List<DepartmentPositionWithRelations>>()
    private val _departmentWithExample =
        MutableLiveData<List<EmployeesWithDepartmentPositions>>()

    private val _departmentWithEmployees =
        MutableLiveData<List<DepartmentWithEmployees>>()

    val departmentPosition: LiveData<DepartmentPosition>
        get() = _departmentPosition

    val employeePositionsListPair: LiveData<List<Pair<EmployeeDepartmentPosition, Employee>>>
        get() = _employeePositionsListPair

    val departmentPositionWithRelations:
            LiveData<List<DepartmentPositionWithRelations>>
        get() = _departmentPositionWithRelations

    val departmentWithExample:
            LiveData<List<EmployeesWithDepartmentPositions>>
        get() = _departmentWithExample


    val departmentWithEmployees:
            LiveData<List<DepartmentWithEmployees>>
        get() = _departmentWithEmployees

/*    fun getAllEmployeesDepartmentPosition() {
        viewModelScope.launch {
            try {
                _employeesDepartmentPositionList.postValue(employeeDepartmentPositionRepository.getAllEmployeeDepartmentPosition())
            } catch (t: Throwable) {
                Timber.e(t, "employeesDepartmentPositionList error")
            }
        }
    }*/

    fun getDepartmentPositionById(departmentPositionId: Long) {
        viewModelScope.launch {
            try {
                _departmentPosition.postValue(
                    departmentPositionRepository.getDepartmentPositionById(
                        departmentPositionId
                    ).first()
                )
            } catch (t: Throwable) {
                Timber.e(t, "departmentPosition error")
            }
        }
    }

    fun getEmployeeDepartmentPositionByDepartmentId(departmentPositionId: Long) {
        viewModelScope.launch {
            try {
                employeeDepartmentPositionRepository.getEmployeeDepartmentPositionByDepartmentId(
                    departmentPositionId
                ).collect { employeesDepartmentPosition ->
                    _employeesDepartmentPositionList.postValue(employeesDepartmentPosition)

                    val tempEmployeeList = employeesDepartmentPosition.map {
                        employeesRepository.getEmployeeById(it.employee_id)
                    }.filterNotNull()
                    val resultList = employeesDepartmentPosition.zip(tempEmployeeList)

                    Timber.d("resultList $resultList")
                    _employeePositionsListPair.postValue(resultList)
                }
            } catch (t: Throwable) {
                Timber.e(t, "employee department position error")
            }
        }
    }

/*    fun getEmployeeById(employeeId: Long) {
        viewModelScope.launch {
            try {
                _employeeList.postValue(employeesRepository.getEmployeeById(employeeId))
            } catch (t: Throwable) {
                Timber.e(t, "employee error")
            }
        }
    }*/

    fun makeRelationsBetweenEmployeeAndDepartmentPositions() {
        Timber.d("start make Relations")
        viewModelScope.launch {
            Timber.d("   make Relations")

            employeesRepository.getAllEmployees().collect { employees ->
                val departmentPositions = departmentPositionRepository.getAllDepartmentPositions()
                val employeesDepartmentPosition = employees.map { employee ->
                    val randomDepartmentPosition = (departmentPositions.indices).random()
                    EmployeeDepartmentPosition(
                        0,
                        employee.id,
                        departmentPositions[randomDepartmentPosition].id
                    )
                }
                Timber.d("employeesDepartmentPosition $employeesDepartmentPosition")
                employeeDepartmentPositionRepository.insertEmployeeDepartmentPosition(
                    employeesDepartmentPosition
                )
            }
        }
    }

    fun getDepartmentPositionWithAllEmployees(departmentPositionId: Long) {
        viewModelScope.launch {
            departmentPositionRepository.getDepartmentPositionWithAllEmployees(departmentPositionId)
                .collect {
                    _departmentPositionWithRelations.postValue(it)
                }
        }
    }

    fun getEmployeesWithDepartmentPositions(departmentPositionId: Long) {
        viewModelScope.launch {
            _departmentWithExample.postValue(
                departmentPositionRepository.getEmployeesWithDepartmentPositions(
                    departmentPositionId
                )
            )
        }
    }

    fun getDepartmentWithEmployees(departmentPositionId: Long) {
        viewModelScope.launch {
            _departmentWithEmployees.postValue(
                departmentPositionRepository.getDepartmentWithEmployees(departmentPositionId)
            )
        }
    }
}