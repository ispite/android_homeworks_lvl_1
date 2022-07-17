package ru.skillbox.a27_31_roomdao.ui.work_departments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.item_page.*
import ru.skillbox.a27_31_roomdao.R
import ru.skillbox.a27_31_roomdao.utils.autoCleared
import ru.skillbox.a27_31_roomdao.utils.withArguments
import timber.log.Timber

class DepartmentPositionsViewPagerFragment : Fragment() {

//    private var myAdapter by AutoClearedValue<WorkDepartmentsViewPagerAdapter>(this)

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
        initList()
//        viewModel.getAllDepartmentPositions()
        bindViewModel()
/*        departmentPositionsExample.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.black
            )
        )*/
        arguments?.let {
//            Timber.d("bundle: $it")
//            container.setBackgroundResource(it.getInt("color"))
//            departmentPositions.text = "Item ${it.getInt("position")}"
//            Timber.d("argument: ${it.getInt(ARG_OBJECT)}")
//            departmentPositionsExample.text = "Item ${it.getString(ARG_STRING)}"


            bindViewModelWithParameter(it.getLong(ARG_WORK_DEPARTMENT_ID))
//            departmentPositions.text = "Item ${it.getInt(ARG_OBJECT)}"
        }

        val args = requireArguments()
//        myAdapter =

/*        args.getInt(KEY_TAB_NUMBER).let {
            departmentPositions.text = "Item $it"
            Timber.d("args value: ${args.getInt(KEY_TAB_NUMBER)}")
        }*/

        /*args.let {
            container.setBackgroundResource(it.getInt("color"))
            departmentPositions.text = "Item ${it.getInt("position")}"
        }*/
    }

    private fun initList() {
        departmentPositionAdapter = DepartmentPositionListAdapter()
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
/*        viewModel.departmentPositionList.observe(viewLifecycleOwner) {
            departmentPositionAdapter.items = it
        }*/
    }

    fun passArguments() {

    }

    companion object {
        const val ARG_OBJECT = "object"
        const val ARG_STRING = "string"
        const val ARG_WORK_DEPARTMENT_ID = "work_department_id"
        private const val KEY_TAB_NUMBER = "tab_number"

        fun newInstance(tabNumber: Int): DepartmentPositionsViewPagerFragment {
            return DepartmentPositionsViewPagerFragment().withArguments {
                putInt(KEY_TAB_NUMBER, tabNumber)
            }
        }
    }
}