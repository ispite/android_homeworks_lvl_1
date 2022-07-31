package ru.skillbox.a27_31_roomdao.ui.work_departments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.skillbox.a27_31_roomdao.data.DepartmentPositionRepository
import ru.skillbox.a27_31_roomdao.data.db.models.DepartmentPosition
import timber.log.Timber

class DepartmentPositionsViewModel : ViewModel() {

    private val departmentPositionRepository = DepartmentPositionRepository()

    private val _departmentPositionList = MutableLiveData<List<DepartmentPosition>>()

    val departmentPositionList: LiveData<List<DepartmentPosition>>
        get() = _departmentPositionList

    fun getAllDepartmentPositions() {
        viewModelScope.launch {
            try {
                _departmentPositionList.postValue(departmentPositionRepository.getAllDepartmentPositions())
            } catch (t: Throwable) {
                Timber.e(t, "department position list error")
            }
        }
    }

    fun getPositionsByWorkDepartmentId(workDepartmentId: Long) {
        viewModelScope.launch {
            try {
                _departmentPositionList.postValue(
                    departmentPositionRepository
                        .getPositionsByWorkDepartmentId(workDepartmentId)
                )
            } catch (t: Throwable) {
                Timber.e(t, "department position list error")
            }
        }
    }

    fun removeDepartmentPosition(departmentPosition: DepartmentPosition) {
        viewModelScope.launch {

        }
    }
}