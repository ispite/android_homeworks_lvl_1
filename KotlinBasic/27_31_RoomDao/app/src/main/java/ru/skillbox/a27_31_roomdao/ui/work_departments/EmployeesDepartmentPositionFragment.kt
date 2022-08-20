package ru.skillbox.a27_31_roomdao.ui.work_departments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_employees_department_position.*
import ru.skillbox.a27_31_roomdao.R
import ru.skillbox.a27_31_roomdao.utils.autoCleared
import timber.log.Timber

class EmployeesDepartmentPositionFragment :
    Fragment(R.layout.fragment_employees_department_position) {

    private val viewModel = EmployeesDepartmentPositionViewModel()
    private val args: EmployeesDepartmentPositionFragmentArgs by navArgs()
    private var employeeDepartmentPositionAdapter: EmployeeDepartmentPositionAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.makeRelationsBetweenEmployeeAndDepartmentPositions()
        initList()
        Timber.d("args ${args.departmentPosition}")
        viewModel.getDepartmentPositionById(args.departmentPosition)
        viewModel.getEmployeeDepartmentPositionByDepartmentId(args.departmentPosition)
        viewModel.departmentPosition.observe(viewLifecycleOwner) {
            departmentPositionTextView.text = it.jobTitle + " " + it.salary
        }
        viewModel.employeePositionsListPair.observe(viewLifecycleOwner) {
            employeeDepartmentPositionAdapter.addData(it)
            employeeDepartmentPositionAdapter.notifyDataSetChanged()

        }

        viewModel.departmentPositionWithRelations.observe(viewLifecycleOwner) {
            Timber.d("LIST $it")
        }
        testDepartmentPositionWithRelation(args.departmentPosition)
        viewModel.getEmployeesWithDepartmentPositions(args.departmentPosition)
        viewModel.departmentWithExample.observe(viewLifecycleOwner) {
            Timber.d("EXAMPLE $it")
        }

        viewModel.getDepartmentWithEmployees(args.departmentPosition)
        viewModel.departmentWithEmployees.observe(viewLifecycleOwner) {
            Timber.d("CORRECT WORK $it")
        }
    }

    private fun initList() {
        employeeDepartmentPositionAdapter = EmployeeDepartmentPositionAdapter()
        with(employeeDepartmentPositionListRecyclerView) {
            adapter = employeeDepartmentPositionAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun testDepartmentPositionWithRelation(departmentPositionId: Long) {
        viewModel.getDepartmentPositionWithAllEmployees(departmentPositionId)
    }
}