package ru.skillbox.a25_29_contentprovider.add_contact

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add_contact.*
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest
import ru.skillbox.a25_29_contentprovider.R
import ru.skillbox.a25_29_contentprovider.utils.hideKeyboardAndClearFocus
import ru.skillbox.a25_29_contentprovider.utils.toast

class AddContactFragment : Fragment(R.layout.fragment_add_contact) {

    //private val phonePattern = Pattern.compile("^\\+?[0-9]{3}-?[0-9]{6,12}\$")
    //private val myPhonePattern = Pattern.compile("""^\+?[0-9]?[-\s]?[0-9]{3}[-\s]?[0-9]{6,12}$""")
    //private val myEmailPattern = Pattern.compile("""^[a-z]@[a-z]\.[a-z]{2,3}$""")

    private val viewModel by viewModels<AddContactViewModel>()

    private var flagName = false
    private var flagLastName = false
    private var flagPhoneNumber = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameCheck()
        lastNameCheck()
        phoneNumberCheck()
        emailCheck()

        bindViewModel()

        addContactButton.setOnClickListener {
            saveContactWithPermissionCheck()
        }
    }

    private fun nameCheck() {
        inputName.doOnTextChanged { text, _, _, _ ->
            if (text!!.length < 2) {
                inputNameTIL.error = "В имени должно быть больше 1ого символа"
            } else {
                inputNameTIL.error = null
                flagName = true
                addContactButton.isEnabled = flagName && flagLastName && flagPhoneNumber
            }
        }
    }

    private fun lastNameCheck() {
        inputLastName.doOnTextChanged { text, _, _, _ ->
            if (text!!.length < 3) {
                inputLastNameTIL.error = "В фамилии должно быть больше 2х символов"
            } else {
                inputLastNameTIL.error = null
                flagLastName = true
                addContactButton.isEnabled = flagName && flagLastName && flagPhoneNumber
            }
        }
    }

    private fun phoneNumberCheck() {
        inputPhoneNumber.doOnTextChanged { text, _, _, _ ->
            if (text!!.isBlank() || android.util.Patterns.PHONE.matcher(text).matches().not()) {
                inputPhoneNumberTIL.error = "Неправильный формат телефонного номера"
            } else {
                inputPhoneNumberTIL.error = null
                flagPhoneNumber = true
                addContactButton.isEnabled = flagName && flagLastName && flagPhoneNumber
            }
        }
    }

    private fun emailCheck() {
        inputEmail.doOnTextChanged { text, _, _, _ ->
            if (text!!.isBlank() || android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
                    .not()
            ) {
                inputEmailTIL.error = "Email неверно написан"
            } else {
                inputEmailTIL.error = null
            }
        }
    }

    private fun bindViewModel() {
        viewModel.saveError.observe(viewLifecycleOwner) { toast(it) }
        viewModel.saveSuccess.observe(viewLifecycleOwner) { findNavController().popBackStack() }
    }

    private fun saveContactWithPermissionCheck() {
        constructPermissionsRequest(
            Manifest.permission.WRITE_CONTACTS,
            onShowRationale = ::onContactPermissionShowRationale,
            onPermissionDenied = ::onContactPermissionDenied,
            onNeverAskAgain = ::onContactPermissionNeverAskAgain
        ) {
            viewModel.saveContact(
                firstName = inputName!!.text!!.toString(),
                lastName = inputLastName!!.text!!.toString(),
                phone = inputPhoneNumber!!.text!!.toString(),
                //email = inputEmail?.text?.toString()?
                email = if (inputEmail?.text?.toString()!!.isNotEmpty()) {
                    inputEmail?.text?.toString()
                } else null

                )
        }.launch()
    }

    private fun onContactPermissionShowRationale(request: PermissionRequest) {
        request.proceed()
    }

    private fun onContactPermissionDenied() {
        toast(R.string.add_contact_permission_denied)
    }

    private fun onContactPermissionNeverAskAgain() {
        toast(R.string.add_contact_permission_never_ask_again)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isRemoving) {
            requireActivity().hideKeyboardAndClearFocus()
        }
    }
}