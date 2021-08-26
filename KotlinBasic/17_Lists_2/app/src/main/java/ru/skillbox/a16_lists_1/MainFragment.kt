package ru.skillbox.a16_lists_1

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment: FragmentActivity(R.layout.fragment_main) {

    private lateinit var adapter: ListFragmentAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.fragment_main)

        val fragmentAdapter = ListFragmentAdapter(supportFragmentManager)

        viewPager.adapter = fragmentAdapter

        //viewPager.adapter = fragmentAdapter

        tabLayout.setupWithViewPager(viewPager)

        /*adapter = ListFragmentAdapter(this)
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = adapter

        tabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "TAB ${(position + 1)}"
        }.attach()*/
    }

/*    fun showTabsWithArticles(typeOfArticles: ArticleTypes = ArticleTypes.EVERYTHING) {



        val adapter = ListFragmentAdapter(articles2, this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(articles2[position].articleCaption)

        }.attach()
    }*/
}