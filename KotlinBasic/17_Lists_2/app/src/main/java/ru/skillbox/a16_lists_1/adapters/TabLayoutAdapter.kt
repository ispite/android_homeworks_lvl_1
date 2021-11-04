package ru.skillbox.a16_lists_1.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.skillbox.a16_lists_1.VehicleListFragment

class TabLayoutAdapter(
    val tabs: Array<Int>,
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return tabs.size
    }

    override fun createFragment(position: Int): Fragment {
        val currentTab = tabs[position]
        return VehicleListFragment.newInstance(currentTab)

    }
}