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
import ru.skillbox.a27_31_roomdao.data.db.models.EmployeeStatus
import ru.skillbox.a27_31_roomdao.utils.SingleLiveEvent
import timber.log.Timber

class AddUpdateEmployeeViewModel : ViewModel() {

    private val employeeRepository = EmployeeRepository()

    private val _existingEmployee = MutableLiveData<Employee>()

    private val _saveError = SingleLiveEvent<Int>()
    private val _saveSuccess = SingleLiveEvent<Unit>()

    val existingEmployee: LiveData<Employee>
        get() = _existingEmployee

    val saveError: LiveData<Int>
        get() = _saveError

    val saveSuccess: LiveData<Unit>
        get() = _saveSuccess

    fun init(employeeId: Long) {
        viewModelScope.launch {
            try {
                val employee = employeeRepository.getEmployeeById(employeeId)
                if (employee != null) _existingEmployee.postValue(employee)
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }

    fun insertEmployee(
        id: Long,
        companyId: Long,
        firstName: String,
        lastName: String,
        birthdate: String,
        status: EmployeeStatus
    ) {
        val employee = Employee(
            id = id,
            companyId = companyId,
            firstName = firstName,
            lastName = lastName,
            birthdate = birthdate,
            status = status
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

    private fun showError(t: Throwable) {
        _saveError.postValue(
            when (t) {
                is IncorrectFormException -> R.string.user_add_error_incorrect_format
                else -> R.string.user_add_error_default
            }
        )
    }
}