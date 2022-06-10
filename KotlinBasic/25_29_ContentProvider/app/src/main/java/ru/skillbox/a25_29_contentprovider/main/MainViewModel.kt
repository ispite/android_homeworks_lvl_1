package ru.skillbox.a25_29_contentprovider.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.skillbox.a25_29_contentprovider.data.Contact
import ru.skillbox.a25_29_contentprovider.utils.SingleLiveEvent

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository(application)

    private val _contacts = MutableLiveData<List<Contact>>()

    val contactsList: LiveData<List<Contact>>
        get() = _contacts

    private val _fileName = MutableLiveData<String>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val _showToast = SingleLiveEvent<Int>()

    val fileName: LiveData<String>
        get() = _fileName

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val showToast: LiveData<Int>
        get() = _showToast

    fun loadList() {
        viewModelScope.launch {
            try {
                _contacts.postValue(repository.getAllContacts())
            } catch (t: Throwable) {
                Log.e("MainViewModel", "loadList: ", t)
                _contacts.postValue(emptyList())
            }
        }
    }

    fun downloadFile(fileUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.checkFileExistence(fileUrl) {
                _fileName.postValue(it)
            }
            if (result) {
                _showToast.postValue(0)
                return@launch
            }
            _isLoading.postValue(true)
            delay(500)
            repository.downloadFile(fileUrl) { success, fileName ->
                if (success) {
                    _fileName.postValue(fileName)
                    _showToast.postValue(1)
                    _fileName.value?.let { saveSharedPrefsInfo(fileUrl, it) }
                }
            }
            _isLoading.postValue(false)
        }
    }

    private fun saveSharedPrefsInfo(fileUrl: String, fileName: String) {
        Log.d("MainViewModel", "saveSharedPrefsInfo fileURL: $fileUrl")
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveSharedPrefsInfo(fileUrl, fileName)
        }
    }
}