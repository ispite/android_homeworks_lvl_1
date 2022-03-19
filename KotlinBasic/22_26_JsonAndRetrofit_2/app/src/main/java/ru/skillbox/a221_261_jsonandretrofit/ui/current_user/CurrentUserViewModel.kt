package ru.skillbox.a221_261_jsonandretrofit.ui.current_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteUser

class CurrentUserViewModel : ViewModel() {
    private val repository = CurrentUserRepository()

    private val _userList = MutableLiveData<List<RemoteUser>>(emptyList())
    private val _isLoading = MutableLiveData<Boolean>(false)

    val userList: LiveData<List<RemoteUser>>
        get() = _userList

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun search(query: String) {
        _isLoading.postValue(true)
        repository.searchUsers(
            query = query,
            onComplete = { users ->
                _isLoading.postValue(false)
                _userList.postValue(users)
            },
            onError = { throwable ->
                _isLoading.postValue(false)
                _userList.postValue(emptyList())
            }
        )
    }

    fun getAuthenticatedUser() {
        _isLoading.postValue(true)
        repository.getAuthenticatedUser({ user ->
            _isLoading.postValue(false)
            _userList.postValue(listOf(user))
        }, { _ ->
            _isLoading.postValue(false)
            _userList.postValue(emptyList())
        })
    }
}