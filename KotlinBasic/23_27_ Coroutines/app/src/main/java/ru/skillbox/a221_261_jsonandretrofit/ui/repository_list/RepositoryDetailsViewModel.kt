package ru.skillbox.a221_261_jsonandretrofit.ui.repository_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RepositoryDetailsViewModel : ViewModel() {
    private val repository = RepositoryRepository()

    private val _repoStarred = MutableLiveData<Boolean>()

    val repoStarred: LiveData<Boolean>
        get() = _repoStarred

    fun checkRepoStared(owner: String, repo: String) {
        viewModelScope.launch {
            try {
                val result = repository.checkRepoStared(owner, repo)
                _repoStarred.postValue(result)
            } catch (t: Throwable) {
                _repoStarred.postValue(false)
            }
        }
    }

    fun starUnstarRepo(owner: String, repo: String) {
        viewModelScope.launch {
            try {
                if (_repoStarred.value == false) {
                    val result = repository.starRepo(owner, repo)
                    Log.d("RepositoryDetailsViewModel", "starUnstarRepo: ${result}")
                    _repoStarred.postValue(true)
                } else {
                    val result = repository.unstarRepo(owner, repo)
                    Log.d("RepositoryDetailsViewModel", "starUnstarRepo: ${result}")
                    _repoStarred.postValue(false)
                }
            } catch (t: Throwable) {
                Log.d("RepositoryDetailsViewModel", "starUnstarRepo: ${t.message}")
            }
        }
    }
}