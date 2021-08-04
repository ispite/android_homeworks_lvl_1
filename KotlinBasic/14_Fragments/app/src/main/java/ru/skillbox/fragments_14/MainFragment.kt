package ru.skillbox.fragments_14

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment


class MainFragment : Fragment(R.layout.fragment_main) {

    private val tagFragment = "MainFragment"
    private var isTwoPane = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Блок кода с условием через ресурсы
        /*val screenType = getString(R.string.screen_type)
        if (screenType == "phone") {
            showListFragment()
        }*/

        val view = super.onCreateView(inflater, container, savedInstanceState)
        val finded =  view?.findViewById<FrameLayout>(R.id.containerFragmentMain1)
        if (finded == null) {
            showListFragment()
        }
        return view
    }

    private fun showListFragment() {
        childFragmentManager.beginTransaction()
            .add(R.id.containerFragmentMain, ListFragment())
            .commit()
    }
}