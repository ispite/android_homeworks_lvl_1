package ru.skillbox.a27_31_roomdao.ui.employees

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.skillbox.a27_31_roomdao.R
import ru.skillbox.a27_31_roomdao.data.EmployeeRepository
import ru.skillbox.a27_31_roomdao.data.IncorrectFormException
import ru.skillbox.a27_31_roomdao.data.db.models.Employee
import ru.skillbox.a27_31_roomdao.utils.SingleLiveEvent
import timber.log.Timber

class EmployeesViewModel : ViewModel() {

    private val employeeRepository = EmployeeRepository()

    private val _employeeList = MutableLiveData<List<Employee>>()

    private val _saveError = SingleLiveEvent<Int>()
    private val _saveSuccess = SingleLiveEvent<Unit>()

    val employeeList: LiveData<List<Employee>>
        get() = _employeeList

    val saveError: LiveData<Int>
        get() = _saveError

    val saveSuccess: LiveData<Unit>
        get() = _saveSuccess

    private val employeeFirstNameList = listOf("Иван", "Сергей", "Владимир")
    private val employeeLastNameList = listOf("Иванов", "Петров", "Сидоров")
    private val employeeBirthdateList = listOf("21.01.1990", "11.05.1976", "29.02.1986")

    fun getAllEmployees() {
        viewModelScope.launch {
            try {
                _employeeList.postValue(employeeRepository.getAllEmployees())
            } catch (t: Throwable) {
                Timber.e(t, "employee list error")
            }
        }
    }

    fun insertEmployee(
        id: Long,
        companyId: Long,
        firstName: String,
        lastName: String,
        birthdate: String
    ) {
        val employee = Employee(
            id = id,
            companyId = companyId,
            firstName = firstName,
            lastName = lastName,
            birthdate = birthdate
        )

        viewModelScope.launch {
            try {
                if (id == 0L) {
                    employeeRepository.insertEmployee(employee)
                } else {
                    employeeRepository.updateEmployee(employee)
                }
                _saveSuccess.postValue(Unit)
            } catch (t: Throwable) {
                Timber.e(t, "employee insert error")
                showError(t)
            }
        }
    }

    fun insertRandomEmployee(
/*        id: Long,
        companyId: Long,
        firstName: String,
        lastName: String,
        birthdate: String*/
    ) {
        val id = 0L
        val employee = Employee(
            id = id,
            companyId = 0,
            firstName = employeeFirstNameList.random(),
            lastName = employeeLastNameList.random(),
            birthdate = employeeBirthdateList.random()
        )

        viewModelScope.launch {
            try {
                if (id == 0L) {
                    employeeRepository.insertEmployee(employee)
                    reloadList()
                } else {
                    employeeRepository.updateEmployee(employee)
                }
                _saveSuccess.postValue(Unit)
            } catch (t: Throwable) {
                Timber.e(t, "employee insert error")
                showError(t)
            }
        }
    }

    fun removeEmployeeById(employee: Employee) {
        viewModelScope.launch {
            try {
                employeeRepository.removeEmployeeById(employee.id)
                reloadList()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

    private fun showError(t: Throwable) {
        _saveError.postValue(
            when (t) {
                is IncorrectFormException -> R.string.user_add_error_incorrect_format
                else -> R.string.user_add_error_default
            }
        )
    }

    fun reloadList() {
        viewModelScope.launch {
            try {
                _employeeList.postValue(employeeRepository.getAllEmployees())
            } catch (t: Throwable) {
                Timber.e(t, "employee reload list error")
                _employeeList.postValue(emptyList())
            }
        }
    }
}