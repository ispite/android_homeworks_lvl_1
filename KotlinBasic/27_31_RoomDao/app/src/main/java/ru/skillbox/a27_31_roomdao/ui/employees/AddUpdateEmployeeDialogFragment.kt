package ru.skillbox.a27_31_roomdao.ui.employees

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.dialog_add_update_employee.view.*
import kotlinx.android.synthetic.main.view_toolbar.view.*
import kotlinx.android.synthetic.main.view_toolbar.view.toolbar
import kotlinx.android.synthetic.main.view_toolbar_double_size.view.*
import ru.skillbox.a27_31_roomdao.R
import ru.skillbox.a27_31_roomdao.data.db.models.Employee
import ru.skillbox.a27_31_roomdao.utils.toast
import timber.log.Timber
import kotlin.random.Random

class AddUpdateEmployeeDialogFragment : DialogFragment() {

    private val args: AddUpdateEmployeeDialogFragmentArgs by navArgs()

    private val viewModel by viewModels<AddUpdateEmployeeViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireParentFragment().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_update_employee, null)
//        val toolbarView = inflater.inflate(R.layout.view_toolbar, null)
        bindViewModel(dialogView)
        viewModel.init(args.id)
//        val toolbarNew = dialogView.addToolbar.toolbar
//        val toolbar = toolbarView.toolbar
        if (args.id != 0L) {
//            toolbarView.minimumHeight = 20
            val toolbarNew = dialogView.updateToolbar
            toolbarNew.visibility = View.VISIBLE
//            toolbarNew.toolbar.title = "Обновление информации о сотруднике"
            toolbarNew.toolbar.toolbarTitleTextView.text = "Обновление информации о сотруднике"
            toolbarNew.toolbar.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

/*            val layoutParams = toolbarNew.toolbar.layoutParams
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            toolbarNew.toolbar.layoutParams = layoutParams*/
//            toolbar.title = "Обновление информации о сотруднике"

        } else {
            val toolbarNew = dialogView.addToolbar
            toolbarNew.visibility = View.VISIBLE
            toolbarNew.toolbar.title = "Добавление нового сотрудника"

//            toolbar.title = "Добавление нового сотрудника"
        }

        return /*val alertDialog =*/ AlertDialog.Builder(requireContext())
            .setView(dialogView)
//            .setCustomTitle(toolbarView)
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Ok") { _, _ ->
                insertEmployee(dialogView)
//                viewModel.saveSuccess.
                findNavController().previousBackStackEntry?.savedStateHandle?.set("REFRESH", Random.nextInt())
                Timber.d("Employee inserted")
            }
            .create()

//        return alertDialog
    }

/*    private fun initToolbar() {
        toolbar.title = "Update employee"
    }*/

    private fun bindViewModel(view: View) {
        viewModel.existingEmployee.observe(this) { setExistingEmployee(view, it) }
        viewModel.saveError.observe(this) { toast(it) }
//        viewModel.saveSuccess.observe(this) { findNavController().popBackStack() }
    }

    private fun setExistingEmployee(view: View, employee: Employee) {
//        firstNameTextField.editText?.setText(employee.firstName)
//        lastNameTextField.editText?.setText(employee.lastName)
//        birthdateTextField.editText?.setText(employee.birthdate)

        view.firstNameTextField.editText?.setText(employee.firstName)
        view.lastNameTextField.editText?.setText(employee.lastName)
        view.birthdateTextField.editText?.setText(employee.birthdate) //this need date picker
    }

    private fun insertEmployee(view: View) {
        viewModel.insertEmployee(
            id = args.id,
            companyId = 0,
            firstName = view.firstNameTextField.editText?.text.toString(),
            lastName = view.lastNameTextField.editText?.text.toString(),
            birthdate = view.birthdateTextField.editText?.text.toString() //this need date picker
        )
    }
}