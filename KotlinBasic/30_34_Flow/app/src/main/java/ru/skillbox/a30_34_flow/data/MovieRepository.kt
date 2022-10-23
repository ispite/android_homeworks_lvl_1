package ru.skillbox.a30_34_flow.data

import ru.skillbox.a30_34_flow.networking.Networking

class MovieRepository {

    suspend fun searchMovies(query: String, movieType: MovieType) {
        Networking.omdbApi.getSearchMovieCall(query, movieType.toString())
    }
}