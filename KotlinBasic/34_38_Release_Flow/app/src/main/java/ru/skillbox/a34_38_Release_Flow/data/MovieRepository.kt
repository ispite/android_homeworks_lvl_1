package ru.skillbox.a34_38_Release_Flow.data

import kotlinx.coroutines.flow.Flow
import ru.skillbox.a34_38_Release_Flow.data.db.Database
import ru.skillbox.a34_38_Release_Flow.data.db.models.MovieDB
import ru.skillbox.a34_38_Release_Flow.networking.Networking
import ru.skillbox.a34_38_Release_Flow.networking.OmdbApiHelperImpl

class MovieRepository {

    private val movieDao = Database.instance.movieDao()
    private val omdbApiHelper = OmdbApiHelperImpl(Networking.omdbApi)

    // NETWORK SECTION

    fun searchMoviesFlow(query: String, movieType: MovieType): Flow<OmdbResponse> =
        omdbApiHelper.getSearchMovieCall(query, movieType.toString())

    // DATABASE SECTION

    suspend fun insertMovie(movie: MovieDB) {
        if (isMovieValid(movie).not()) throw RuntimeException("incorrect form")
        movieDao.insertMovies(listOf(movie))
    }

    private fun isMovieValid(movie: MovieDB): Boolean {
        return movie.title.isNotBlank() &&
                movie.type.isNotBlank() &&
                movie.imdbId.isNotBlank()
    }

    suspend fun getMovieByTitleAndType(title: String, type: String) =
        movieDao.getMovieByTitleAndType(title, type)

    fun observeMovies() = movieDao.observeMovies()
}