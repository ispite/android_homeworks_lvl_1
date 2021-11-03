package ru.skillbox.a16_lists_1

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_main.*
import ru.skillbox.a16_lists_1.adapters.TabLayoutAdapter

class MainFragment: FragmentActivity(R.layout.fragment_main) {

/*    private lateinit var adapter: ListFragmentAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.fragment_main)

        showFragments()

//        val fragmentAdapter = ListFragmentAdapter(supportFragmentManager)

//        viewPager.adapter = fragmentAdapter

        //viewPager.adapter = fragmentAdapter

//        tabLayout.setupWithViewPager(viewPager)

        /*adapter = ListFragmentAdapter(this)
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = adapter

        tabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "TAB ${(position + 1)}"
        }.attach()*/
    }

    fun showFragments() {
//        val adapter = ListFragmentAdapter(this)
//        viewPager.adapter = adapter
        val tabs = arrayOf<Int>(0, 1, 2)
        val adapter = TabLayoutAdapter(tabs, this)
        viewPager.adapter = adapter


        val tabNames = arrayOf(
            "Linear tab",
            "Grid tab",
            "Staggered Grid tab"
        )
        TabLayoutMediator(tabLayout, viewPager) { tab, position->
            tab.text = tabNames[position]
        }.attach()
    }

/*    fun showTabsWithArticles(typeOfArticles: ArticleTypes = ArticleTypes.EVERYTHING) {



        val adapter = ListFragmentAdapter(articles2, this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(articles2[position].articleCaption)

        }.attach()
    }*/
}