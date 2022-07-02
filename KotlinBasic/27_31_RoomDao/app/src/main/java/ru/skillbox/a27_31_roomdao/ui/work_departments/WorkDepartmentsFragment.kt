package ru.skillbox.a27_31_roomdao.ui.work_departments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.skillbox.a27_31_roomdao.R
import ru.skillbox.a27_31_roomdao.data.db.Database
import ru.skillbox.a27_31_roomdao.data.db.models.DepartmentPosition
import ru.skillbox.a27_31_roomdao.data.db.models.WorkDepartment

class WorkDepartmentsFragment : Fragment(R.layout.fragment_work_departments) {

    private val workDepartmentDao = Database.instance.workDepartmentDao()
    private val departmentPositionDao = Database.instance.departmentPositionDao()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveWorkDepartments()
        saveDepartmentPositions()
    }

    private fun saveWorkDepartments() {
//        Timber.d("saveWorkDepartments")
        val workDepartments = listOf(
            WorkDepartment(
                id = 0,
                companyId = 0,
                workDepartmentName = "Производство"
            ),
            WorkDepartment(
                id = 0,
                companyId = 0,
                workDepartmentName = "Бухгалтерия"
            ),
            WorkDepartment(
                id = 0,
                companyId = 0,
                workDepartmentName = "АСУ ТП"
            )
        )
        lifecycleScope.launch {
            workDepartmentDao.insertWorkDepartment(workDepartments)
        }
    }

    private fun saveDepartmentPositions() {
        val departmentPosition = listOf(
            DepartmentPosition(
                id = 0,
                workDepartmentId = 1,
                jobTitle = "Слесарь",
                salary = 50000.00
            ),
            DepartmentPosition(
                id = 0,
                workDepartmentId = 1,
                jobTitle = "Токарь",
                salary = 65000.00
            ),
            DepartmentPosition(
                id = 0,
                workDepartmentId = 2,
                jobTitle = "Бухгалтер",
                salary = 50000.00
            ),
            DepartmentPosition(
                id = 0,
                workDepartmentId = 3,
                jobTitle = "Инженер АСУ ТП",
                salary = 50000.00
            )
        )
        lifecycleScope.launch {
            departmentPositionDao.insertDepartmentPosition(departmentPosition)
        }
    }
}