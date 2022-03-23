package ru.skillbox.a221_261_jsonandretrofit.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.skillbox.a221_261_jsonandretrofit.ui.current_user.CurrentUserRepository

class MainViewModel : ViewModel() {
    private val repository = MainRepository()
    private val repositoryForUser = CurrentUserRepository()

    private val _userBio = MutableLiveData<String>()

    val userBio: LiveData<String>
        get() = _userBio

    fun checkBio() {
        viewModelScope.launch {
            try {
                val user = repositoryForUser.getAuthenticatedUser()
                _userBio.postValue(user.bio.orEmpty())
            } catch (t: Throwable) {
                _userBio.postValue("")
            }
        }
    }

    fun patchBio(bioFromTextInput: String) {
        repository.changeBio(bioFromTextInput, {

        }, {

        })
    }
}