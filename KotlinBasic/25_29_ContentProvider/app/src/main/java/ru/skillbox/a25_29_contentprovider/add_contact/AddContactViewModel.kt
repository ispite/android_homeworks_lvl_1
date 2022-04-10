package ru.skillbox.a25_29_contentprovider.add_contact

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.skillbox.a25_29_contentprovider.R
import ru.skillbox.a25_29_contentprovider.main.MainRepository
import ru.skillbox.a25_29_contentprovider.utils.SingleLiveEvent

class AddContactViewModel(application: Application) : AndroidViewModel(application) {

    private val contactRepository = MainRepository(application)

    private val _saveSuccess = SingleLiveEvent<Unit>()
    private val _saveError = SingleLiveEvent<Int>()

    val saveSuccess: LiveData<Unit>
        get() = _saveSuccess

    val saveError: LiveData<Int>
        get() = _saveError

    fun saveContact(firstName: String, lastName: String, phone: String, email: String) {
        viewModelScope.launch {
            try {
                contactRepository.saveContact(firstName, lastName, phone, email)
                _saveSuccess.postValue(Unit)
            } catch (t: Throwable) {
                Log.e("AddContactViewModel", "saveContact error:", t)
                showError(t)
            }
        }
    }

    private fun showError(t: Throwable) {
        _saveError.postValue(
            when(t) {
                is IncorrectFormException -> R.string.add_contact_save_error_form
                else -> R.string.add_contact_save_error
            }
        )
    }
}