package ru.skillbox.a30_34_flow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import ru.skillbox.a30_34_flow.data.MovieType
import timber.log.Timber

class MainViewModel : ViewModel() {

    lateinit var job: Job

    private val _resultFlow = MutableLiveData<Pair<String, MovieType>>()
    val resultFlow: LiveData<Pair<String, MovieType>>
        get() = _resultFlow

    fun bind(queryFlow: Flow<String>, movieTypeFlow: Flow<MovieType>) {
//        viewModelScope.launch {
/*        movieTypeFlow
            .onEach { Timber.d("movieType $movieTypeFlow") }
            .launchIn(viewModelScope)*/

        job = combine(
            queryFlow,
            movieTypeFlow/*.onStart { emit(MovieType.MOVIE) }*/
        ) { query, movieType ->
            query to movieType
        }
            .debounce(250)
            .distinctUntilChanged()
            .onEach { Timber.d("query to movieType $it") }
            .launchIn(viewModelScope)
/*                .collect {
                    Timber.d("_resultFlow $it")
                    _resultFlow.postValue(it)
                }*/
//        }
    }

    fun cancelJob() {
        job.cancel()
    }
}