package ru.skillbox.a30_34_flow.data

import ru.skillbox.a30_34_flow.data.db.Database
import ru.skillbox.a30_34_flow.data.db.models.MovieDB
import ru.skillbox.a30_34_flow.networking.Networking

class MovieRepository {

    private val movieDao = Database.instance.movieDao()

    suspend fun searchMovies(query: String, movieType: MovieType)/*: OmdbResponse*/ {
        Networking.omdbApi.getSearchMovieCall(query, movieType.toString())
    }

/*    suspend fun insertMovie(movie: MovieDB) {
        if (isMovieValid(movie).not()) throw RuntimeException("incorrect form")
        movieDao.insertMovies(listOf(movie))
    }*/

/*    private fun isMovieValid(movie: MovieDB): Boolean {
        return movie.title.isNotBlank() &&
                movie.type.isNotBlank() &&
                movie.imdbId.isNotBlank()
    }*/
}