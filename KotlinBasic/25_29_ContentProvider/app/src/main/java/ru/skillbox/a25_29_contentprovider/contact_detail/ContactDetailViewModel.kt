package ru.skillbox.a25_29_contentprovider.contact_detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.skillbox.a25_29_contentprovider.data.ContactInfo
import ru.skillbox.a25_29_contentprovider.main.MainRepository
import ru.skillbox.a25_29_contentprovider.utils.SingleLiveEvent

class ContactDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val contactInfoRepository = MainRepository(application)

    private val _contactInfoList = MutableLiveData<List<ContactInfo>>(emptyList())
    private val _contactName = MutableLiveData<String>()
    private val _deleteState = SingleLiveEvent<Int>()

    val contactInfoList: LiveData<List<ContactInfo>>
        get() = _contactInfoList

    val contactName: LiveData<String>
        get() = _contactName

    val deleteState: LiveData<Int>
        get() = _deleteState

    fun getPhonesAndEmails(id: Long) {
        viewModelScope.launch {
            _contactInfoList.postValue(contactInfoRepository.getPhonesAndEmailsForContact(id))
        }
    }

    fun getContact(id: Long) {
        viewModelScope.launch {
            _contactName.postValue(contactInfoRepository.getContact(id).first().name ?: "EMPTY")
        }
    }

    fun removeContact(id: Long) {
        viewModelScope.launch {
            try {
                contactInfoRepository.removeContact(id)
                _deleteState.postValue(1)
            } catch (t: Throwable) {
                Log.e("ContactDetailViewModel", "removeContact:", t)
            }
        }
    }
}