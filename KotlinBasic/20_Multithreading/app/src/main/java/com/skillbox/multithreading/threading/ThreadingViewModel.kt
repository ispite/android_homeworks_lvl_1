package com.skillbox.multithreading.threading

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.multithreading.SingleLiveEvent
import com.skillbox.multithreading.networking.Movie

// The Matrix 1999 tt0133093
// Fight Club 1999 tt0137523
// Forrest Gump 1994 tt0109830
// The Godfather 1972 tt0068646
// The Lord of the Rings: The Two Towers 2002 tt0167261

class ThreadingViewModel(/*private val repository: MovieRepository*/) : ViewModel() {

    private val userRepository = MovieRepository()

    private val movieIds = listOf(
        "tt0111161",
        "tt0068646",
        "tt0468569",
        "tt0108052",
        "tt0060196",
        "tt0167260",
        "tt0137523",
        "tt0073486",
        "tt0468569",
        "tt0108052",
        "tt0060196",
        "tt0167260",
        "tt0167260",
        "tt0137523",
        "tt0073486",
        "tt0468569",
        "tt0108052",
        "tt0060196",
        "tt0167260",
        "tt0137523",
        "tt0073486"
    )

    private val movieIDsForMainThread = listOf(
        "tt0133093", // The Matrix 1999 tt0133093
        "tt0137523", // Fight Club 1999 tt0137523
        "tt0109830", // Forrest Gump 1994 tt0109830
        "tt0068646", // The Godfather 1972 tt0068646
        "tt0167261"  // The Lord of the Rings: The Two Towers 2002 tt0167261
    )

    private val timeLiveData = MutableLiveData<Long>()
    private val moviesLiveData = MutableLiveData<String>()
    private val moviesListData = MutableLiveData<List<Movie>>()
    private val showToastLiveData = SingleLiveEvent<Unit>()

    val time: LiveData<Long?>
        get() = timeLiveData

    val movies: LiveData<String?>
        get() = moviesLiveData

    val moviesList: LiveData<List<Movie>>
        get() = moviesListData

    val showToast: LiveData<Unit>
        get() = showToastLiveData

    fun requestMovies() {
        Log.d("ThreadTest", "requestMovies start on ${Thread.currentThread().name}")
        userRepository.fetchMovies(movieIds, movieIDsForMainThread) { movies, fetchTime ->
            Log.d("ThreadTest", "requestMovies fetched on ${Thread.currentThread().name}")
            timeLiveData.postValue(fetchTime)
            moviesListData.postValue(movies)
            showToastLiveData.postValue(Unit)
        }
        //showToastLiveData.postValue(Unit)
        Log.d("ThreadTest", "requestMovies end on ${Thread.currentThread().name}")
    }
}