package ru.skillbox.a21_networking.movie_search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.Call
import java.io.IOException

class MovieSearchViewModel : ViewModel() {
    private val repository = MovieSearchRepository()

    private var currentCall: Call? = null

    private val movieListLiveData = MutableLiveData<List<RemoteMovie>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<IOException>()

    val movieList: LiveData<List<RemoteMovie>>
        get() = movieListLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val error: LiveData<IOException>
        get() = errorLiveData

    lateinit var lastTitle: String
    lateinit var lastYear: String
    lateinit var lastType: String

/*    fun search(text: String) {
        isLoadingLiveData.postValue(true)
        currentCall = repository.searchMovie(text) { movies ->
            isLoadingLiveData.postValue(false)
            movieListLiveData.postValue(movies)
            currentCall = null
        }
    }*/

    fun searchWithParameters(title: String, year: String, type: String) {
        //errorLiveData.postValue(null)
        lastTitle = title
        lastYear = year
        lastType = type
        isLoadingLiveData.postValue(true)
        currentCall = repository.searchMovieWithParameters(title, year, type, { movies ->
            isLoadingLiveData.postValue(false)
            movieListLiveData.postValue(movies)
            currentCall = null
        }, { errorCallback ->
            errorLiveData.postValue(errorCallback)
        })
/*         if (errorLiveData.value != null) {
             throw errorLiveData.value!!
        }*/
    }

    fun resendRequest() {
        isLoadingLiveData.postValue(true)
        currentCall =
            repository.searchMovieWithParameters(lastTitle, lastYear, lastType, { movies ->
                isLoadingLiveData.postValue(false)
                movieListLiveData.postValue(movies)
                currentCall = null
            }, { errorCallback -> errorLiveData.postValue(errorCallback) })
    }

    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }
}