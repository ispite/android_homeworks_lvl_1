package ru.skillbox.a16_lists_1

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*
import ru.skillbox.a16_lists_1.adapters.TabLayoutAdapter

class MainFragment : FragmentActivity(R.layout.fragment_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showFragments()
    }

    fun showFragments() {
        val tabs = arrayOf<Int>(0, 1, 2)
        val adapter = TabLayoutAdapter(tabs, this)
        viewPager.adapter = adapter

        val tabNames = arrayOf(
            "Linear tab",
            "Grid tab",
            "Staggered Grid tab"
        )
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }

}