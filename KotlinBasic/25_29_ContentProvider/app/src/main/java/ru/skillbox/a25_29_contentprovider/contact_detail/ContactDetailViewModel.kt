package ru.skillbox.a25_29_contentprovider.contact_detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.skillbox.a25_29_contentprovider.data.ContactInfo
import ru.skillbox.a25_29_contentprovider.main.MainRepository

class ContactDetailViewModel(application: Application):AndroidViewModel(application) {

    private val contactInfoRepository = MainRepository(application)

    private val _contactInfoList = MutableLiveData<List<ContactInfo>>(emptyList())

    val contactInfoList: LiveData<List<ContactInfo>>
        get() = _contactInfoList

    fun getPhonesAndEmails(id: Long) {
        contactInfoRepository.getPhonesForContact(id)
        _contactInfoList.postValue(emptyList())
    }
}