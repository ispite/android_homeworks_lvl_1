package ru.skillbox.a27_31_roomdao.ui.work_departments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.skillbox.a27_31_roomdao.data.db.models.WorkDepartment
import ru.skillbox.a27_31_roomdao.ui.work_departments.DepartmentPositionsViewPagerFragment.Companion.ARG_OBJECT
import ru.skillbox.a27_31_roomdao.ui.work_departments.DepartmentPositionsViewPagerFragment.Companion.ARG_STRING
import ru.skillbox.a27_31_roomdao.ui.work_departments.DepartmentPositionsViewPagerFragment.Companion.ARG_WORK_DEPARTMENT_ID

class TabLayoutAdapter(
    private val tabs: List<WorkDepartment>,
    fragment: FragmentActivity
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): Fragment {
        val fragment = DepartmentPositionsViewPagerFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_OBJECT, position + 1)
            putString(ARG_STRING, tabs[position].workDepartmentName)
            putLong(ARG_WORK_DEPARTMENT_ID, tabs[position].id)
        }
        return fragment
    }
}