package ru.skillbox.a27_31_roomdao.ui.employees

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.skillbox.a27_31_roomdao.data.EmployeeRepository
import ru.skillbox.a27_31_roomdao.data.db.models.Employee
import timber.log.Timber

class AddUpdateEmployeeViewModel : ViewModel() {

    private val employeeRepository = EmployeeRepository()

    private val _existingEmployee = MutableLiveData<Employee>()

    val existingEmployee: LiveData<Employee>
        get() = _existingEmployee

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


}