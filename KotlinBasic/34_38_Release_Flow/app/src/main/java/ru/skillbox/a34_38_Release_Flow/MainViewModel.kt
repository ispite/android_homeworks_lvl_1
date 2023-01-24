package ru.skillbox.a34_38_Release_Flow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import ru.skillbox.a34_38_Release_Flow.data.Movie
import ru.skillbox.a34_38_Release_Flow.data.MovieRepository
import ru.skillbox.a34_38_Release_Flow.data.MovieType
import ru.skillbox.a34_38_Release_Flow.data.db.models.MovieDB
import timber.log.Timber
import java.io.IOException

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
                repository.searchMoviesFlow(pair.first, pair.second)
                    .retryWhen { cause, attempt ->
                        Timber.d("retryWhen attempt= $attempt")
                        delay(500)
                        cause is IOException && attempt < 2
                    }
                    .catch {
                        Timber.e("exception $it")
                        val dbResponse =
                            repository.getMovieByTitleAndType(pair.first, pair.second.toString())
                        val movieList = dbResponse.map { movieDb ->
                            MovieDB.convertFromDb(movieDb)
                        }
                        _videoList.postValue(movieList)

                    }
                    .onEach { omdbResponse ->
                        val movieList = omdbResponse.search ?: emptyList()
                        Timber.d("movieList $movieList")
                        movieList.let { _videoList.postValue(it) }

                        repository.observeMovies()
                            .map { movieDbList ->
                                val imdbIdMovieDbList = movieDbList.map { it.imdbId }
                                val newMovies =
                                    movieList.filterNot { movie -> imdbIdMovieDbList.contains(movie.id) }
                                Timber.d("newMovies size=${newMovies.size} newMovies: $newMovies ")
                            }.launchIn(viewModelScope)
                        movieList.forEach { movie ->
                            repository.insertMovie(MovieDB.convertFromResponse(movie))
                        }
                    }
                    .launchIn(viewModelScope)
            }
            .launchIn(viewModelScope)
    }

    fun cancelJob() {
        job.cancel()
    }
}