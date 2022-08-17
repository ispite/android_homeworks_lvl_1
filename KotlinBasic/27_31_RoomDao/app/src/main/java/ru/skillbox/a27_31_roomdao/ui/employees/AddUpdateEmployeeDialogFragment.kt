package ru.skillbox.a27_31_roomdao.ui.employees

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.dialog_add_update_employee.view.*
import kotlinx.android.synthetic.main.view_toolbar.view.toolbar
import kotlinx.android.synthetic.main.view_toolbar_double_size.view.*
import kotlinx.coroutines.*
import ru.skillbox.a27_31_roomdao.R
import ru.skillbox.a27_31_roomdao.data.db.models.Employee
import ru.skillbox.a27_31_roomdao.data.db.models.EmployeeStatus
import ru.skillbox.a27_31_roomdao.utils.toast
import timber.log.Timber
import kotlin.random.Random

class AddUpdateEmployeeDialogFragment : DialogFragment() {

    private val args: AddUpdateEmployeeDialogFragmentArgs by navArgs()

    private val viewModel by viewModels<AddUpdateEmployeeViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireParentFragment().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_update_employee, null)
        bindViewModel(dialogView)
        viewModel.init(args.id)
        if (args.id != 0L) {
            val toolbarNew = dialogView.updateToolbar
            toolbarNew.visibility = View.VISIBLE
            toolbarNew.toolbar.toolbarTitleTextView.text = "Обновление информации о сотруднике"
            toolbarNew.toolbar.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

        } else {
            val toolbarNew = dialogView.addToolbar
            toolbarNew.visibility = View.VISIBLE
            toolbarNew.toolbar.title = "Добавление нового сотрудника"
        }

        return AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Ok") { _, _ ->
                /*lifecycleScope.launch {
                    delay(5000)
                }*/
                runBlocking {
                    GlobalScope.launch {
                        insertEmployeeAsync(dialogView).await()
                        delay(5000)
//                    Thread.sleep(5000)
                    }
                }

                runBlocking {
                    lifecycleScope.launch {
                        delay(5000)
                    }
                }

                Timber.d("Employee inserted")
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    "REFRESH",
                    Random.nextInt()
                )
            }
            .create()
    }

    private fun bindViewModel(view: View) {
        viewModel.existingEmployee.observe(this) { setExistingEmployee(view, it) }
        viewModel.saveError.observe(this) { toast(it) }
    }

    private fun setExistingEmployee(view: View, employee: Employee) {
        view.firstNameTextField.editText?.setText(employee.firstName)
        view.lastNameTextField.editText?.setText(employee.lastName)
        view.birthdateTextField.editText?.setText(employee.birthdate) //this need date picker
    }

    private suspend fun insertEmployeeAsync(view: View): Deferred<Boolean> {
        return viewModel.insertEmployeeAsync(
            id = args.id,
            companyId = 0,
            firstName = view.firstNameTextField.editText?.text.toString(),
            lastName = view.lastNameTextField.editText?.text.toString(),
            birthdate = view.birthdateTextField.editText?.text.toString(), //this need date picker
            status = EmployeeStatus.values().toList().random()
        )
    }
}