package ru.skillbox.fragments_14

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment


class MainFragment : Fragment(R.layout.fragment_main) {

    private val tagFragment = "MainFragment"
    private var isTwoPane = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        determinePaneLayout()

        /*if (isTwoPane)  {
            showListFragment()
        }*/

        val screenType = getString(R.string.screen_type)
        if (screenType == "phone") {
            showListFragment()
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun showListFragment() {
        childFragmentManager.beginTransaction()
            .add(R.id.containerFragmentMain, ListFragment())
            .commit()
    }

    private fun determinePaneLayout() {
        val fragmentItemDetail = view?.findViewById<FrameLayout>(R.id.fragmentItemsList)
        // If there is a second pane for details
        if (fragmentItemDetail != null) {
            isTwoPane = true
        }
    }
}