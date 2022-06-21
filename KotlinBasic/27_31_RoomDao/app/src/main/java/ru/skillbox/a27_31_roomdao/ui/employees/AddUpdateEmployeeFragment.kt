package ru.skillbox.a27_31_roomdao.ui.employees

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.view_toolbar.*
import kotlinx.android.synthetic.main.view_toolbar.view.*
import ru.skillbox.a27_31_roomdao.R
import ru.skillbox.a27_31_roomdao.utils.inflate

class AddUpdateEmployeeFragment : DialogFragment() {

    private val args: AddUpdateEmployeeFragmentArgs by navArgs()



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireParentFragment().layoutInflater
        val view = inflater.inflate(R.layout.dialog_add_update_employee, null)
//        initToolbar()
//        val toolbar = toolbar.inflate(R.id.toolbar)
//        val toolbar2 = inflater.inflate(R.id.toolbar, null)
        val toolbar3 = inflater.inflate(R.layout.view_toolbar, null)
//        val toolbar4 = view.toolbar.context
        val title = view.findViewById<Toolbar>(R.id.toolbar)
        val title2 = toolbar3.toolbar
        title2.title = "asdasd"
//        title.toolbar.title = "ASDasd"

        val alertDialog= AlertDialog.Builder(requireContext())
            .setView(view)
//            .setTitle("asdasd")
            .setCustomTitle(toolbar3)
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Ok") { _, _ -> }
            .create()

        return alertDialog
    }

    private fun initToolbar() {
        toolbar.title = "Update employee"
    }
}