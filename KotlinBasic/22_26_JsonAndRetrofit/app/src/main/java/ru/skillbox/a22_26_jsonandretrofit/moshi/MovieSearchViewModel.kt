package ru.skillbox.a22_26_jsonandretrofit.moshi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.Call
import java.io.IOException
import java.net.URL

class MovieSearchViewModel:ViewModel() {

    private val repository = MovieSearchRepository()

    private var currentCall: Call? = null

    private val movieLiveData = MutableLiveData<Movie>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<IOException?>()

    val movieViewModel: LiveData<Movie>
        get() = movieLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val error: LiveData<IOException?>
        get() = errorLiveData

    lateinit var lastTitle: String

    fun searchWithParameters(title: String/*, year: String, type: String*/) {
        lastTitle = title

        isLoadingLiveData.postValue(true)
        currentCall = repository.searchMovie(title, { movie ->
            isLoadingLiveData.postValue(false)
            Log.d("ViewModel", "BEFORE searchWithParameters: $movieLiveData")
            movie?.let { movieLiveData.postValue(it) } ?: run {
                Log.d("ViewModel", "searchWithParameters: $movieLiveData")
                movieLiveData.postValue(
                    Movie(
                        "",
                        0,
                        AgeRating.GENERAL,
                        "",
                        URL("http://kotlinlang.org"),
                        mapOf("" to "")
                    )
                )
                Log.d("ViewModel", "searchWithParameters: $movieLiveData")
            }
/*            if (movie != null) {
                    (movieLiveData.postValue(movie))
                }*/
            currentCall = null
        }, { errorCallback ->
            errorLiveData.postValue(errorCallback)
        })
    }

    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }
}