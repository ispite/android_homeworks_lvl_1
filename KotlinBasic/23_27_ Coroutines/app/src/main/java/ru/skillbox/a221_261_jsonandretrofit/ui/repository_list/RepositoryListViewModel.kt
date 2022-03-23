package ru.skillbox.a221_261_jsonandretrofit.ui.repository_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteRepository

class RepositoryListViewModel : ViewModel() {
    private val repository = RepositoryRepository()
    private val myViewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val _reposList = MutableLiveData<List<RemoteRepository>>(emptyList())
    private val _isLoading = MutableLiveData<Boolean>(false)

    val reposList: LiveData<List<RemoteRepository>>
        get() = _reposList

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getAuthenticatedRepositories() {
        myViewModelScope.launch {
            this.isActive.let {
                _isLoading.postValue(true)
                try {
                    val reposList = repository.getAuthenticatedRepository()
                    _reposList.postValue(reposList)
                } catch (t: Throwable) {
                    _reposList.postValue(emptyList())
                } finally {
                    _isLoading.postValue(false)
                }
            }
        }
    }

    fun getStarredRepos(username: String) {
        repository.getStarredRepositories(username, { reposList ->
            _reposList.postValue(reposList)
        }, {
            _reposList.postValue(emptyList())
        })
    }

    override fun onCleared() {
        super.onCleared()
        myViewModelScope.coroutineContext.cancelChildren()
    }
}