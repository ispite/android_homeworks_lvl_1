package ru.skillbox.a221_261_jsonandretrofit.ui.current_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteUser

class CurrentUserViewModel : ViewModel() {
    private val repository = CurrentUserRepository()
    private val scope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { coroutineContext, throwable -> })

    private val _userList = MutableLiveData<List<RemoteUser>>(emptyList())
    private val _isLoading = MutableLiveData<Boolean>(false)
    private val _followingList = MutableLiveData<List<RemoteUser>>(emptyList())

    val userList: LiveData<List<RemoteUser>>
        get() = _userList

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val followingList: LiveData<List<RemoteUser>>
        get() = _followingList

    fun search(query: String) {
        scope.launch {
            _followingList.postValue(emptyList())
            try {
                val users = repository.searchUsers(query)
                _userList.postValue(users)
            } catch (t: Throwable) {
                _userList.postValue(emptyList())
            }
        }
    }

    fun getAuthenticatedUser() {
        scope.launch {
            try {
                val user = repository.getAuthenticatedUser()
                _userList.postValue(listOf(user))
            } catch (t: Throwable) {
                _userList.postValue(emptyList())
            }
        }
    }

    fun getUserIsFollowing() {
        scope.launch {
            try {
                val followingList = repository.getUserIsFollowing()
                //userListLiveData.postValue(userListLiveData.value?.plus(followingList) ?: emptyList())
                _followingList.postValue(followingList)
            } catch (t: Throwable) {
                _followingList.postValue(emptyList())
            }
        }
    }
}