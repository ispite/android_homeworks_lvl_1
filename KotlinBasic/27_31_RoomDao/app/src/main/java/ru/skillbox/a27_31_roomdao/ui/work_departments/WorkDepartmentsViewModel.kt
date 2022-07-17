package ru.skillbox.a27_31_roomdao.ui.work_departments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.skillbox.a27_31_roomdao.data.WorkDepartmentRepository
import ru.skillbox.a27_31_roomdao.data.db.models.WorkDepartment
import timber.log.Timber

class WorkDepartmentsViewModel: ViewModel() {

    private val workDepartmentRepository = WorkDepartmentRepository()

    private val _workDepartmentList = MutableLiveData<List<WorkDepartment>>()

    val workDepartmentList: LiveData<List<WorkDepartment>>
        get() = _workDepartmentList

    fun getAllWorkDepartments() {
        viewModelScope.launch {
            try {
                _workDepartmentList.postValue(workDepartmentRepository.getAllWorkDepartments())
            } catch (t: Throwable) {
                Timber.e(t, "workDepartment list error")
            }
        }
    }
}