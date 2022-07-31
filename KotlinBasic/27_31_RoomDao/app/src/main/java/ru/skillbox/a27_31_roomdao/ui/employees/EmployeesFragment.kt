package ru.skillbox.a27_31_roomdao.ui.employees

import android.os.Bundle
import android.os.SystemClock.sleep
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_employees.*
import kotlinx.coroutines.delay
import ru.skillbox.a27_31_roomdao.R
import ru.skillbox.a27_31_roomdao.data.db.models.Employee
import ru.skillbox.a27_31_roomdao.ui.employees.adapter.EmployeeListAdapter
import ru.skillbox.a27_31_roomdao.utils.autoCleared
import timber.log.Timber

class EmployeesFragment : Fragment(R.layout.fragment_employees) {

    private val viewModel = EmployeesViewModel()
    private var employeeAdapter: EmployeeListAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        addEmployeeFab.setOnClickListener {
            findNavController().navigate(R.id.action_employeesFragment_to_addUpdateEmployeeDialogFragment)
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>("REFRESH")?.observe(viewLifecycleOwner) {
//            sleep(500)
            Timber.d("it work $it")
            viewModel.reloadList()
        }
        addRandomEmployeeFab.setOnClickListener {
            viewModel.insertRandomEmployee()
        }
        bindViewModel()
        viewModel.reloadList()
    }

    private fun initList() {
        employeeAdapter =
            EmployeeListAdapter(::navigateToEmployeeEdit, viewModel::removeEmployeeById)
        with(employeeListRecyclerView) {
            adapter = employeeAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun bindViewModel() {
        viewModel.employeeList.observe(viewLifecycleOwner) {
            employeeAdapter.items = it
        }
    }

    private fun navigateToEmployeeEdit(employee: Employee) {
        val direction =
            EmployeesFragmentDirections.actionEmployeesFragmentToAddUpdateEmployeeDialogFragment(employee.id)
        findNavController().navigate(direction)
    }
}