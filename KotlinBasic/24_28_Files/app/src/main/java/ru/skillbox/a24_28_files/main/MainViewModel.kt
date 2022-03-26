package ru.skillbox.a24_28_files.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.skillbox.a24_28_files.utils.SingleLiveEvent

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = MainRepository(application)

    private val _fileName = MutableLiveData<String>()
    private val _isLoading = MutableLiveData<Boolean>()
    private val _showToast = SingleLiveEvent<Unit>()
    private val _showToast2 = SingleLiveEvent<Unit>()

    val fileName: LiveData<String>
        get() = _fileName

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val showToast: LiveData<Unit>
        get() = _showToast

    val showToast2: LiveData<Unit>
        get() = _showToast2

    fun downloadFile(fileUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.checkFileExistence(fileUrl) {
                _fileName.postValue(it)
            }
            if (!result) {
                _isLoading.postValue(true)
                delay(1000)
                repository.downloadFile(fileUrl, {
                    _fileName.postValue(it)
                    Log.d("MainViewModel", "fileName: $it")
                }, { success ->
                    if (success) {
                        _showToast.postValue(Unit)
                        _fileName.value?.let { saveSharedPrefsInfo(fileUrl, it) }
                    }
                })
                _isLoading.postValue(false)
            } else {
                _showToast2.postValue(Unit)
            }
        }
    }

    private fun saveSharedPrefsInfo(fileUrl: String, fileName: String) {
        Log.d("MainViewModel", "saveSharedPrefsInfo fileURL: $fileUrl")
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveSharedPrefsInfo(fileUrl, fileName)
        }
    }

    fun downloadOnFirstRun() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.downloadOnFirstRun()
        }
    }
}