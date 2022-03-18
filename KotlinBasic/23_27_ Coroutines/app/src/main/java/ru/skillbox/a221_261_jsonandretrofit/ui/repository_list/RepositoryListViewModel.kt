package ru.skillbox.a221_261_jsonandretrofit.ui.repository_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
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

        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                val reposList = repository.getAuthenticatedRepository()
                reposListLiveData.postValue(reposList)
            } catch (t: Throwable) {
                reposListLiveData.postValue(emptyList())
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    fun getStarredRepos(username: String) {
        repository.getStarredRepositories(username, { reposList ->
            reposListLiveData.postValue(reposList)
        }, {
            reposListLiveData.postValue(emptyList())
        })
    }
}