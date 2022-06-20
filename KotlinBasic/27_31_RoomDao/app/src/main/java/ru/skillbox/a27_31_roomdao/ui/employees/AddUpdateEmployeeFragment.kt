package ru.skillbox.a27_31_roomdao.ui.employees

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.skillbox.a27_31_roomdao.R

class AddUpdateEmployeeFragment : DialogFragment(R.layout.dialog_add_update_employee) {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireParentFragment().layoutInflater
        val view = inflater.inflate(R.layout.dialog_add_update_employee, null)

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Ok") { _, _ -> }
            .create()
    }
}