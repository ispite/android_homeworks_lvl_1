package ru.skillbox.a34_38_Release_Flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import ru.skillbox.a34_38_Release_Flow.data.MovieRepository
import ru.skillbox.a34_38_Release_Flow.data.db.models.MovieDB

class DbListViewModel : ViewModel() {

    private val repository = MovieRepository()

//    private val _videoList = MutableLiveData<List<Movie>>()
//    val videoList: LiveData<List<Movie>>
//        get() = _videoList

    val videoListFlow = repository.observeMovies().mapLatest { listMovieDb: List<MovieDB> ->
        listMovieDb.map { MovieDB.convertFromDb(it) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
//        initialValue = Result.Loading
    )

/*    fun observeVideos() {
        viewModelScope.launch {
            repository.observeMovies().collect { listMovieDb ->
                val listMovie = listMovieDb.map { MovieDB.convertFromDb(it) }
                _videoList.postValue(listMovie)
            }
        }
    }*/
}