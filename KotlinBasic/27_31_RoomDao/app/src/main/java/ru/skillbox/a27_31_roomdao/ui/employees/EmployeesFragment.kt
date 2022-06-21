package ru.skillbox.a27_31_roomdao.ui.employees

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_employees.*
import ru.skillbox.a27_31_roomdao.R
import ru.skillbox.a27_31_roomdao.ui.employees.adapter.EmployeeListAdapter
import ru.skillbox.a27_31_roomdao.utils.autoCleared

class EmployeesFragment : Fragment(R.layout.fragment_employees) {

    private val viewModel = EmployeesViewModel()
    private var employeeAdapter: EmployeeListAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        addEmployeeFab.setOnClickListener {
            findNavController().navigate(R.id.action_employeesFragment_to_addUpdateEmployeeFragment)
        }
        addRandomEmployeeFab.setOnClickListener {
            viewModel.insertRandomEmployee()
        }
        bindViewModel()
        viewModel.reloadList()
    }

    private fun initList() {
        employeeAdapter = EmployeeListAdapter({ ; }, viewModel::removeEmployeeById)
        with(employeeListRecyclerView) {
            adapter = employeeAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun bindViewModel() {
        viewModel.employeeList.observe(viewLifecycleOwner) {
            employeeAdapter.items = it
//            viewModel.reloadList()
        }
    }
}