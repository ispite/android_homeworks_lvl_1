package ru.skillbox.a221_261_jsonandretrofit.ui.current_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteUser

class CurrentUserViewModel : ViewModel() {
    private val repository = CurrentUserRepository()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { coroutineContext, throwable ->  })

    private val userListLiveData = MutableLiveData<List<RemoteUser>>(emptyList())
    private val isLoadingLiveData = MutableLiveData<Boolean>(false)
    private val followingListLiveData = MutableLiveData<List<RemoteUser>>(emptyList())

    val userList: LiveData<List<RemoteUser>>
        get() = userListLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val followingList: LiveData<List<RemoteUser>>
        get() = followingListLiveData

    fun search(query: String) {
        scope.launch {
            followingListLiveData.postValue(emptyList())
            try {
                val users = repository.searchUsers(query)
                userListLiveData.postValue(users)
            } catch (t: Throwable) {
                userListLiveData.postValue(emptyList())
            }
        }
    }

    fun getAuthenticatedUser() {
        scope.launch {
            try {
                val user = repository.getAuthenticatedUser()
                userListLiveData.postValue(listOf(user))
            } catch (t: Throwable) {
                userListLiveData.postValue(emptyList())
            }
        }
    }

    fun getUserIsFollowing() {
        scope.launch {
            try {
                val followingList = repository.getUserIsFollowing()
                //userListLiveData.postValue(userListLiveData.value?.plus(followingList) ?: emptyList())
                followingListLiveData.postValue(followingList)
            } catch (t: Throwable) {
                followingListLiveData.postValue(emptyList())
            }
        }
    }
}