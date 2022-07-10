package ru.skillbox.a27_31_roomdao.ui.work_departments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.item_page.*
import ru.skillbox.a27_31_roomdao.R
import ru.skillbox.a27_31_roomdao.utils.AutoClearedValue
import ru.skillbox.a27_31_roomdao.utils.withArguments

class DepartmentPositionsViewPagerFragment : Fragment() {

//    private var myAdapter by AutoClearedValue<WorkDepartmentsViewPagerAdapter>(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        departmentPositions.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        arguments?.let {

            container.setBackgroundResource(it.getInt("color"))
            departmentPositions.text = "Item ${it.getInt("position")}"
//            Timber.d("argument: ${it.getInt(ARG_OBJECT)}")
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

    fun passArguments() {

    }

    companion object {
        const val ARG_OBJECT = "object"
        private const val KEY_TAB_NUMBER = "tab_number"

        fun newInstance(tabNumber: Int): DepartmentPositionsViewPagerFragment {
            return DepartmentPositionsViewPagerFragment().withArguments {
                putInt(KEY_TAB_NUMBER, tabNumber)
            }
        }
    }
}