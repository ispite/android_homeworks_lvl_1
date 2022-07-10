package ru.skillbox.a27_31_roomdao.ui.work_departments

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.skillbox.a27_31_roomdao.data.db.models.WorkDepartment
import timber.log.Timber

class WorkDepartmentsFragmentStateAdapter(fa: FragmentActivity) :
    FragmentStateAdapter(fa) {

    private val colors = intArrayOf(
        android.R.color.black,
        android.R.color.holo_red_light,
        android.R.color.holo_blue_dark
//        android.R.color.holo_purple
    )

//    lateinit var list: List<WorkDepartment>

/*    fun getItem(position: Int): Fragment = DepartmentPositionsViewPagerFragment().apply {
        arguments = bundleOf(
            "color" to colors[position],
            "position" to position
        )
    }*/

    override fun getItemCount(): Int = colors.size

    override fun createFragment(position: Int): Fragment {
        val fragment = DepartmentPositionsViewPagerFragment()
/*        fragment.arguments = Bundle().apply {
            Timber.d("put argument from FSA: ${position + 1}")
            putInt(DepartmentPositionsViewPagerFragment.Companion.ARG_OBJECT, position + 1)
        }*/

        fragment.apply {
            arguments = bundleOf(
                "color" to colors[position],
                "position" to position
            )
        }

        return fragment
    }
}