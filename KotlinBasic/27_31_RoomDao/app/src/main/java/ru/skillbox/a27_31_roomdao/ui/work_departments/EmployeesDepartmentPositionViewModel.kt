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
    private val _employeeList = MutableLiveData<Employee>()
    private val _employeePositionsListPair =
        MutableLiveData<List<Pair<EmployeeDepartmentPosition, Employee>>>()
    private val _departmentPositionWithRelations =
        MutableLiveData<List<DepartmentPositionWithRelations>>()
    private val _departmentWithExample =
        MutableLiveData<List<EmployeesWithDepartmentPositions>>()

    //    private val _departmentAnotherTry =
//        MutableLiveData<List<EmployeesDepartmentPositionsNew>>()

/*    private val _departmentPositionWithEmployees =
        MutableLiveData<List<DepartmentPositionWithEmployees>>()*/

    private val _departmentWithEmployees =
        MutableLiveData<List<DepartmentWithEmployees>>()

    val employeesDepartmentPositionList: LiveData<List<EmployeeDepartmentPosition>>
        get() = _employeesDepartmentPositionList

    val departmentPosition: LiveData<DepartmentPosition>
        get() = _departmentPosition

    val employeeList: LiveData<Employee>
        get() = _employeeList

    val employeePositionsListPair: LiveData<List<Pair<EmployeeDepartmentPosition, Employee>>>
        get() = _employeePositionsListPair

    val departmentPositionWithRelations:
            LiveData<List<DepartmentPositionWithRelations>>
        get() = _departmentPositionWithRelations

    val departmentWithExample:
            LiveData<List<EmployeesWithDepartmentPositions>>
        get() = _departmentWithExample

//    val departmentAnotherTry:
//            LiveData<List<EmployeesDepartmentPositionsNew>>
//        get() = _departmentAnotherTry

/*    val departmentPositionWithEmployees:
            LiveData<List<DepartmentPositionWithEmployees>>
        get() = _departmentPositionWithEmployees*/

    val departmentWithEmployees:
            LiveData<List<DepartmentWithEmployees>>
        get() = _departmentWithEmployees

    fun getAllEmployeesDepartmentPosition() {
        viewModelScope.launch {
            try {
                _employeesDepartmentPositionList.postValue(employeeDepartmentPositionRepository.getAllEmployeeDepartmentPosition())
            } catch (t: Throwable) {
                Timber.e(t, "employeesDepartmentPositionList error")
            }
        }
    }

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

                    //                for (employeeId in tempEmployeesDepartmentPositionList)
                    val tempEmployeeList = employeesDepartmentPosition.map {
                        employeesRepository.getEmployeeById(it.employee_id)
                    }.filterNotNull()
//                val resultList = mutableListOf<Pair<EmployeeDepartmentPosition, Employee>>()
//                for (i in tempEmployeesDepartmentPositionList.indices) {
//                    tempEmployeeList[i]?.let {
//                        resultList.add(tempEmployeesDepartmentPositionList[i] to it)
//                        Timber.d("resultList in loop $resultList")
//                    }
//                }
                    val resultList = employeesDepartmentPosition.zip(tempEmployeeList)

                    Timber.d("resultList $resultList")
                    _employeePositionsListPair.postValue(resultList)
                }


//                employeesRepository.getEmployeeById(tempEmployeesDepartmentPositionList[0].employee_id)
            } catch (t: Throwable) {
                Timber.e(t, "employee department position error")
            }
        }
    }

    fun getEmployeeById(employeeId: Long) {
        viewModelScope.launch {
            try {
                _employeeList.postValue(employeesRepository.getEmployeeById(employeeId))
            } catch (t: Throwable) {
                Timber.e(t, "employee error")
            }
        }
    }

    fun makeRelationsBetweenEmployeeAndDepartmentPositions() {
        Timber.d("start make Relations")
        viewModelScope.launch {
            Timber.d("   make Relations")
//            val employees = employeesRepository.getAllEmployees().asLiveData().value
//            val employees = employeesRepository.getAllEmployees().flattenToList()
//            Timber.d("employees ${employees}")

            employeesRepository.getAllEmployees().collect { employees ->
                val departmentPositions = departmentPositionRepository.getAllDepartmentPositions()
//            Timber.d("departmentPositions $departmentPositions")
//            var employeesDepartmentPosition: MutableList<EmployeeDepartmentPosition>? = null
/*            for (employee in employees) {
                val randomDepartmentPosition = (departmentPositions.indices).random()
                employeesDepartmentPosition?.add(EmployeeDepartmentPosition(0, employee.id, departmentPositions[randomDepartmentPosition].id))
            }*/

//                for (employee in employees) {
//                    val randomDepartmentPosition = (departmentPositions.indices).random()
//                    employeesDepartmentPosition?.add(EmployeeDepartmentPosition(0, employee.id, departmentPositions[randomDepartmentPosition].id))

                val employeesDepartmentPosition = employees.map { employee ->
                    val randomDepartmentPosition = (departmentPositions.indices).random()
                    //                Timber.d("employee $employee")
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

/*    fun getDepartmentPositionWithEmployees(departmentPositionId: Long) {
        viewModelScope.launch {
            _departmentPositionWithEmployees.postValue(
                departmentPositionRepository
                    .getDepartmentPositionWithEmployees(departmentPositionId)
            )
        }
    }*/

    fun getDepartmentWithEmployees(departmentPositionId: Long) {
        viewModelScope.launch {
            _departmentWithEmployees.postValue(
                departmentPositionRepository.getDepartmentWithEmployees(departmentPositionId)
            )
        }
    }

//    fun getAnotherTry(departmentPositionId: Long) {
//        viewModelScope.launch {
//            _departmentAnotherTry.postValue(departmentPositionRepository.getAnotherTry(departmentPositionId))
//        }
//    }
}