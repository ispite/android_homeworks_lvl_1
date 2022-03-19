package ru.skillbox.a221_261_jsonandretrofit.ui.repository_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteRepository

class RepositoryListViewModel : ViewModel() {
    private val repository = RepositoryRepository()

    private val _reposList = MutableLiveData<List<RemoteRepository>>(emptyList())
    private val _isLoading = MutableLiveData<Boolean>(false)

    val reposList: LiveData<List<RemoteRepository>>
        get() = _reposList

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getAuthenticatedRepositories() {
        _isLoading.postValue(true)
        repository.getAuthenticatedRepository({ reposList ->
            _reposList.postValue(reposList)
            _isLoading.postValue(false)
        }, {
            _reposList.postValue(emptyList())
            _isLoading.postValue(false)
        })
    }

    fun getStarredRepos(username: String) {
        repository.getStarredRepositories(username, { reposList ->
            _reposList.postValue(reposList)
        }, {
            _reposList.postValue(emptyList())
        })
    }
}