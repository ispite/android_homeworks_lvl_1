package ru.skillbox.a27_31_roomdao.ui.work_departments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import ru.skillbox.a27_31_roomdao.R

class EmployeesDepartmentPositionFragment :
    Fragment(R.layout.fragment_employees_department_position) {

    private val viewModel = EmployeesDepartmentPositionViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        viewModel.makeRelationsBetweenEmployeeAndDepartmentPositions()
    }

    private fun initList() {

    }

}