package ru.skillbox.a16_lists_1

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ListFragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
//            0 -> LinearFragment()
            0 -> VehicleListFragment_Dynamic()
            1 -> GridFragment()
            else -> StaggeredGridFragment()
        }
    }
}

/*class ListFragmentAdapter(fragment: FragmentManager) : FragmentPagerAdapter(fragment) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                LinearFragment()
            }
            1 -> GridFragment()
            else -> {
                return StaggeredGridFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "First Tab"
            1 -> "Second Tab"
            else -> {
                return "Third Tab"
            }
        }
    }

}*/

/*class ListFragmentAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        //val fragment = LinearFragment()
        *//*val fragment1 = LinearFragment()
        val fragment2 = LinearFragment()
        val fragment3 = LinearFragment()*//*
        *//*fragment.arguments = Bundle().apply {
            putInt(LinearFragment.ARG_OBJECT, position + 1)
        }*//*
        //return fragment
        return when (position) {
            0 -> LinearFragment()
            1 -> GridFragment()
            2 -> StaggeredGridFragment()
            else -> { throw error("wrong position")  }
        }

    }

}*/

/*class SampleFragmentPagerAdapter(fm: FragmentManager?, private val context: Context) :
    FragmentPagerAdapter(fm!!) {
    val PAGE_COUNT = 3
    private val tabTitles = arrayOf("Tab1", "Tab2", "Tab3")
    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getItem(position: Int): Fragment {
        return PageFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // генерируем заголовок в зависимости от позиции
        return tabTitles[position]
    }
}*/

/*class ListFragmentAdapter(fm: FragmentManager?, private val context: Context) :
    FragmentPagerAdapter(fm!!) {
    val PAGE_COUNT = 3
    private val tabTitles = arrayOf("Tab1", "Tab2", "Tab3")
    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getItem(position: Int): Fragment {
        return LinearFragment.newInstance(position + 1) as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // генерируем заголовок в зависимости от позиции
        return tabTitles[position]
    }
}*/


/*
class ListFragmentAdapter(fm: FragmentManager?, private val context: Context):
 FragmentPagerAdapter(fm!!) {
    val PAGE_COUNT = 3
    private val tabTitles = arrayOf("Tab1", "Tab2", "Tab3")
    private var context: Context? = null

    fun ListFragmentAdapter(fm: FragmentManager?, context: Context?) {
        //super(fm)
        this.context = context
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    fun getItem(position: Int): Fragment? {
        //return LinearFragment.newInstance(position + 1)
        LinearFragment.
        //return LinearFragment.newInstance(1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        // генерируем заголовок в зависимости от позиции
        return tabTitles[position]
    }
}
*/
