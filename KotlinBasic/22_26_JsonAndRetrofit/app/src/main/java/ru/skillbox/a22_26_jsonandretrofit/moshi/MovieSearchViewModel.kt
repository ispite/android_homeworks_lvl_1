package ru.skillbox.a22_26_jsonandretrofit.moshi

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.Call
import java.io.IOException

class MovieSearchViewModel : ViewModel() {

    private val mainHandler = Handler(Looper.getMainLooper())

    private val repository = MovieSearchRepository()

    private var currentCall: Call? = null

    private val movieListLiveData = MutableLiveData<List<Movie>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val errorLiveData = MutableLiveData<IOException?>()
    private val jsonLiveData = MutableLiveData<String>()

    val movieViewModel: LiveData<List<Movie>>
        get() = movieListLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val error: LiveData<IOException?>
        get() = errorLiveData

    val publicJsonLiveData: LiveData<String>
        get() = jsonLiveData

    lateinit var lastTitle: String

    fun searchWithParameters(title: String) {
        lastTitle = title

        isLoadingLiveData.postValue(true)
        currentCall = repository.searchMovie(title, { movie ->
            isLoadingLiveData.postValue(false)
            Log.d("ViewModel", "BEFORE searchWithParameters: $movieListLiveData")
            movie.let { movieListLiveData.postValue(movie) }
            currentCall = null
        }, { errorCallback ->
            errorLiveData.postValue(errorCallback)
        })
    }

    fun replaceScoreToMovie() {
        movieListLiveData.value?.get(0)
            ?.let {
                movieListLiveData.postValue(repository.replaceScoreToMovie(it))
                Log.d("ViewModel", "replaceScoreToMovie: ${movieListLiveData.value}")
            }
    }

    fun convertMovieToJson() {
        movieListLiveData.value!![0].let {
            Log.d("ViewModel", "CALLING convertMovieToJson: ")
            val jsonAnswer = repository.convertCustomMovieInstanceToJson(it)
            Log.d("ViewModel", "convertMovieToJson: $jsonAnswer")
            mainHandler.post {
                jsonLiveData.setValue(jsonAnswer)
            }

        }
        Log.d("ViewModel", "convertMovieToJson LIVEDATA: ${jsonLiveData.value}")
    }

    override fun onCleared() {
        super.onCleared()
        currentCall?.cancel()
    }
}