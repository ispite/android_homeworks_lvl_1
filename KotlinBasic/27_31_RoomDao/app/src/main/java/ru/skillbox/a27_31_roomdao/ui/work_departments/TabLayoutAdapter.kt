package ru.skillbox.a27_31_roomdao.ui.work_departments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.skillbox.a27_31_roomdao.ui.work_departments.DepartmentPositionsViewPagerFragment.Companion.ARG_OBJECT
import timber.log.Timber

class TabLayoutAdapter(
    private val tabs: List<Int>,
    fragment: FragmentActivity
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = tabs.size

    override fun createFragment(position: Int): Fragment {
        val currentTab = tabs[position]
//        return DepartmentPositionsViewPagerFragment.newInstance(currentTab)
        val fragment = DepartmentPositionsViewPagerFragment()
        fragment.arguments = Bundle().apply {
            putInt(ARG_OBJECT, position + 1)

        }
        Timber.d("fragment: $fragment")
        return fragment
    }

}