package ru.skillbox.a27_31_roomdao.ui.employees

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.view_toolbar.*
import kotlinx.android.synthetic.main.view_toolbar.view.*
import ru.skillbox.a27_31_roomdao.R

class AddUpdateEmployeeFragment : DialogFragment() {

    private val args: AddUpdateEmployeeFragmentArgs by navArgs()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireParentFragment().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_update_employee, null)

        val toolbarView = inflater.inflate(R.layout.view_toolbar, null)
        val toolbar = toolbarView.toolbar
        if (args.id != 0L) {
            toolbarView.minimumHeight = 20
            toolbar.title = "Обновление информации о сотруднике"
        } else {
            toolbar.title = "Добавление нового сотрудника"
        }

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCustomTitle(toolbarView)
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Ok") { _, _ -> }
            .create()

        return alertDialog
    }

    private fun initToolbar() {
        toolbar.title = "Update employee"
    }
}