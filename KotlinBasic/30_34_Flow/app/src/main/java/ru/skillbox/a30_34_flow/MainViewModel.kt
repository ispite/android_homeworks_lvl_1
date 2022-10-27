package ru.skillbox.a30_34_flow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import ru.skillbox.a30_34_flow.data.MovieRepository
import ru.skillbox.a30_34_flow.data.MovieType
import ru.skillbox.a30_34_flow.data.db.models.MovieDB

class MainViewModel : ViewModel() {

    private val repository = MovieRepository()

    lateinit var job: Job

    private val _resultFlow = MutableLiveData<Pair<String, MovieType>>()
    val resultFlow: LiveData<Pair<String, MovieType>>
        get() = _resultFlow

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
            .onEach { omdbResponse ->
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