package ru.skillbox.a25_29_contentprovider.main

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.skillbox.a25_29_contentprovider.data.Contact
import ru.skillbox.a25_29_contentprovider.utils.SingleLiveEvent

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository(application)

    private val _contacts = MutableLiveData<List<Contact>>()
    private val _shareIntent = MutableLiveData<Intent>()

    val contactsList: LiveData<List<Contact>>
        get() = _contacts

    val shareIntent : LiveData<Intent>
        get() = _shareIntent

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

    fun downloadFile(fileUrl: String)/*:String*/ {
//        var fileNameReturn : String
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.checkFileExistence(fileUrl) {
//                Log.d("MainViewModel", "checkFileExistence fileName: $it")
                _fileName.postValue(it)
//                fileNameReturn = it
            }
            Log.d("MainViewModel", "result: $result")
            if (result) {
                _showToast.postValue(0)
                return@launch
            }
            _isLoading.postValue(true)
            delay(500)
            repository.downloadFile(fileUrl) { success, fileName ->
                Log.d("MainViewModel", "downloadFile fileName: $fileName")
                if (success) {
                    _fileName.postValue(fileName)
                    _showToast.postValue(1)
//                    _fileName.value?.let { saveSharedPrefsInfo(fileUrl, it) }
                    _fileName.value?.let { saveSharedPrefsInfo(fileUrl, fileName) }
//                    fileNameReturn = fileName
//                    ::fileName.let { saveSharedPrefsInfo(fileUrl, fileName) }
                }
            }
            _isLoading.postValue(false)
            return@launch
        }
//        return fileName.value!!
    }

    private fun saveSharedPrefsInfo(fileUrl: String, fileName: String) {
        Log.d("MainViewModel", "saveSharedPrefsInfo fileURL: $fileUrl, fileName: $fileName")
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveSharedPrefsInfo(fileUrl, fileName)
        }
    }

    fun shareFile(/*downloadedFileName: String*/) {
        viewModelScope.launch(Dispatchers.Main) {
//            Log.d("MainViewModel", "shareFile fileName: ${fileName.value}")
//            Log.d("MainViewModel", "shareFile _fileName: ${_fileName.value}")
//            fileName.value?.let {  }
//            delay(500)
            _shareIntent.postValue(repository.shareFile(fileName.value!!))
        }
    }
}