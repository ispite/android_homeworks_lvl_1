package ru.skillbox.a221_261_jsonandretrofit.ui.current_user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteUser

class CurrentUserViewModel: ViewModel() {
    private val repository = CurrentUserRepository()

    private val userListLiveData = MutableLiveData<List<RemoteUser>>(emptyList())
    private val isLoadingLiveData = MutableLiveData<Boolean>(false)

    val userList: LiveData<List<RemoteUser>>
        get() = userListLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun search(query: String) {
        //Log.d("ViewModel", "search: $query")
        isLoadingLiveData.postValue(true)
        repository.searchUsers(
            query = query,
            onComplete = { users ->
                isLoadingLiveData.postValue(false)
                userListLiveData.postValue(users)
            },
            onError = { throwable ->
                isLoadingLiveData.postValue(false)
                userListLiveData.postValue(emptyList())
            }
        )
    }
}