package ru.skillbox.a27_31_roomdao.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_main.*
import ru.skillbox.a27_31_roomdao.R

class MainFragment: Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        employeesButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_employeesFragment)
        }
    }
}