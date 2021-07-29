package ru.skillbox.fragments_14

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class MainFragment:Fragment(R.layout.fragment_main) {

    private val tagFragment = "MainFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showListFragment()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main)
        DebugLogger.d(tagFragment, "onCreate was called")

        showListFragment()
    }*/

    private fun showListFragment() {
        childFragmentManager.beginTransaction()
            .add(R.id.containerFragmentMain, ListFragment())
            .commit()
    }

    /*private fun showDetailFragment() {
        childFragmentManager.beginTransaction()
            .replace(R.id.containerFragmentMain, DetailFragment())
            .commit()
    }*/



}