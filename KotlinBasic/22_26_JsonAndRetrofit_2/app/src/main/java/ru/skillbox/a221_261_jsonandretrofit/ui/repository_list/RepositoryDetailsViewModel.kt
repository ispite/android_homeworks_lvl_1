package ru.skillbox.a221_261_jsonandretrofit.ui.repository_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbox.a221_261_jsonandretrofit.utils.SingleLiveEvent

class RepositoryDetailsViewModel : ViewModel() {
    private val repository = RepositoryRepository()

    private val _repoStared = MutableLiveData<Boolean>()
    private val _errorStared = SingleLiveEvent<String>()

    val repoStared: LiveData<Boolean>
        get() = _repoStared

    val errorStared:LiveData<String>
        get() = _errorStared

    fun checkRepoStared(owner: String, repo: String) {
        repository.checkRepoStared(owner, repo, {
            _repoStared.postValue(true)
        }, {
            _repoStared.postValue(false)
        })
    }

    fun starUnstarRepo(owner: String, repo: String) {
        if (_repoStared.value == false) {
            repository.starRepo(owner, repo, {
                _repoStared.postValue(true)
            }, {
                _repoStared.postValue(false)
                _errorStared.postValue(it.message)
            })
        } else {
            repository.unstarRepo(
                owner, repo, {
                    _repoStared.postValue(false)
                }, {
                    _repoStared.postValue(true)
                    _errorStared.postValue(it.message)
                }
            )
        }
    }
}