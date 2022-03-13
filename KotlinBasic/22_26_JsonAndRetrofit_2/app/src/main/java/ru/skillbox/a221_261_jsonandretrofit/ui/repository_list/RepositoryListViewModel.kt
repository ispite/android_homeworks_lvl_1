package ru.skillbox.a221_261_jsonandretrofit.ui.repository_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteRepository

class RepositoryListViewModel : ViewModel() {
    private val repository = RepositoryRepository()

    private val reposListLiveData = MutableLiveData<List<RemoteRepository>>(emptyList())
    private val isLoadingLiveData = MutableLiveData<Boolean>(false)

    val reposList: LiveData<List<RemoteRepository>>
        get() = reposListLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun getAuthenticatedRepositories() {
        isLoadingLiveData.postValue(true)
        repository.getAuthenticatedRepository({ reposList ->
            reposListLiveData.postValue(reposList)
            isLoadingLiveData.postValue(false)
        }, {
            reposListLiveData.postValue(emptyList())
            isLoadingLiveData.postValue(false)
        })
    }

    fun getStarredRepos(username: String) {
        repository.getStarredRepositories(username, { reposList ->
            reposListLiveData.postValue(reposList)
        }, {
            reposListLiveData.postValue(emptyList())
        })
    }
}