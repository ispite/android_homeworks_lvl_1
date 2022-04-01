package ru.skillbox.a25_29_contentprovider.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.skillbox.a25_29_contentprovider.data.Contact

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository(application)
    private val _contacts = MutableLiveData<List<Contact>>()

    val contactsList: LiveData<List<Contact>>
        get() = _contacts

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
}