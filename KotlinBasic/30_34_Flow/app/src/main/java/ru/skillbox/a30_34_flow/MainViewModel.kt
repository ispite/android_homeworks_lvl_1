package ru.skillbox.a30_34_flow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import ru.skillbox.a30_34_flow.data.Movie
import ru.skillbox.a30_34_flow.data.MovieRepository
import ru.skillbox.a30_34_flow.data.MovieType
import ru.skillbox.a30_34_flow.data.db.models.MovieDB
import timber.log.Timber

class MainViewModel : ViewModel() {

    private val repository = MovieRepository()

    lateinit var job: Job

    private val _videoList = MutableLiveData<List<Movie>>()
    val videoList: LiveData<List<Movie>>
        get() = _videoList

    fun bind(queryFlow: Flow<String>, movieTypeFlow: Flow<MovieType>) {
        job = combine(
            queryFlow,
            movieTypeFlow
        ) { query, movieType ->
            query to movieType
        }
            .debounce(500)
            .distinctUntilChanged()
            .mapLatest { pair ->
                repository.searchMovies(pair.first, pair.second)
            }
            .catch { Timber.e("exception $it") }
            .onEach { omdbResponse ->
                omdbResponse.search?.let { _videoList.postValue(it) }
                omdbResponse.search?.forEach { movie ->
                    repository.insertMovie(MovieDB.convertFromResponse(movie))
                }
            }
            .launchIn(viewModelScope)
    }

    fun cancelJob() {
        job.cancel()
    }
}