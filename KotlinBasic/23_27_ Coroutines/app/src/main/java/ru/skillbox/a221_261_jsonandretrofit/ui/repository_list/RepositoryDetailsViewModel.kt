package ru.skillbox.a221_261_jsonandretrofit.ui.repository_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RepositoryDetailsViewModel : ViewModel() {
    private val repository = RepositoryRepository()

    private val repoStaredLiveData = MutableLiveData<Boolean>()

    val repoStared: LiveData<Boolean>
        get() = repoStaredLiveData

    fun checkRepoStared(owner: String, repo: String) {
        viewModelScope.launch {
            try {
                val result = repository.checkRepoStared(owner, repo)
                repoStaredLiveData.postValue(result)
            } catch (t: Throwable) {
                repoStaredLiveData.postValue(false)
            }
        }
    }

    fun starUnstarRepo(owner: String, repo: String) {
        if (repoStaredLiveData.value == false) {
            repository.starRepo(owner, repo, {
                repoStaredLiveData.postValue(true)
            }, {
                repoStaredLiveData.postValue(false)
            })
        } else {
            repository.unstarRepo(
                owner, repo, {
                    repoStaredLiveData.postValue(false)
                }, {
                    repoStaredLiveData.postValue(true)
                }
            )
        }
    }
}