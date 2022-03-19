package ru.skillbox.a221_261_jsonandretrofit.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbox.a221_261_jsonandretrofit.ui.current_user.CurrentUserRepository
import ru.skillbox.a221_261_jsonandretrofit.utils.SingleLiveEvent

class MainViewModel : ViewModel() {
    private val repository = MainRepository()
    private val repositoryForUser = CurrentUserRepository()

    private val _userBio = MutableLiveData<String>()
    private val _errorBio = SingleLiveEvent<String>()

    val userBio: LiveData<String>
        get() = _userBio

    val errorBio:LiveData<String>
        get() = _errorBio

    fun checkBio() {
        repositoryForUser.getAuthenticatedUser({ user ->
            _userBio.postValue(user.bio.orEmpty())
        }, {
            _userBio.postValue("")
        })
    }

    fun patchBio(bioFromTextInput: String) {
        repository.changeBio(bioFromTextInput, {
            Log.d("Patch", "patchBio: $it")
        }, {
            _errorBio.postValue(it.message)
        })
    }
}