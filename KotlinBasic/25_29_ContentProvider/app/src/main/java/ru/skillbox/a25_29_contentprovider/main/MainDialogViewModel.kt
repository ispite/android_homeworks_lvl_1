package ru.skillbox.a25_29_contentprovider.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainDialogViewModel : ViewModel() {

    private val _downloadLink = MutableLiveData<String>()

    val downloadLink: LiveData<String>
        get() = _downloadLink

    fun storeLink(downloadLink: String, mainDialogCallback: (String) -> Unit) {
        _downloadLink.postValue(downloadLink)
        mainDialogCallback(downloadLink)
        Log.d("MainDialogViewModel", "onViewCreated: ${downloadLink}")
    }

}