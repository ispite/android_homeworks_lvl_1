package ru.skillbox.a27_31_roomdao.ui.work_departments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.item_page.*
import ru.skillbox.a27_31_roomdao.R
import ru.skillbox.a27_31_roomdao.data.db.models.DepartmentPosition
import ru.skillbox.a27_31_roomdao.utils.autoCleared

class DepartmentPositionsViewPagerFragment : Fragment() {

    private val viewModel = DepartmentPositionsViewModel()
    private var departmentPositionAdapter: DepartmentPositionListAdapter by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindViewModel()
        initList()
        arguments?.let {
            bindViewModelWithParameter(it.getLong(ARG_WORK_DEPARTMENT_ID))
        }
    }

    private fun initList() {
        departmentPositionAdapter = DepartmentPositionListAdapter(
            ::navigateToEmployeesDepartmentPosition,
            viewModel::removeDepartmentPosition
        )
        with(departmentPositions) {
            adapter = departmentPositionAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun bindViewModel() {
        viewModel.departmentPositionList.observe(viewLifecycleOwner) {
            departmentPositionAdapter.items = it
        }
    }

    private fun bindViewModelWithParameter(workDepartmentId: Long) {
        viewModel.getPositionsByWorkDepartmentId(workDepartmentId)
    }

    private fun navigateToEmployeesDepartmentPosition(departmentPosition: DepartmentPosition) {
        val direction =
            WorkDepartmentsFragmentDirections.actionWorkDepartmentsFragmentToEmployeesDepartmentPositionFragment(
                departmentPosition.id
            )
        findNavController().navigate(direction)
    }

    companion object {
        const val ARG_OBJECT = "object"
        const val ARG_STRING = "string"
        const val ARG_WORK_DEPARTMENT_ID = "work_department_id"
        private const val KEY_TAB_NUMBER = "tab_number"
    }
}