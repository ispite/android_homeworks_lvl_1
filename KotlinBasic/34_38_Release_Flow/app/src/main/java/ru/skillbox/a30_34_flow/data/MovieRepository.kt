package ru.skillbox.a30_34_flow.data

import kotlinx.coroutines.flow.Flow
import ru.skillbox.a30_34_flow.data.db.Database
import ru.skillbox.a30_34_flow.data.db.models.MovieDB
import ru.skillbox.a30_34_flow.networking.Networking
import ru.skillbox.a30_34_flow.networking.OmdbApiHelperImpl

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