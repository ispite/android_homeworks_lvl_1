package ru.skillbox.a30_34_flow.networking

import ru.skillbox.a30_34_flow.data.Movie

interface OmdbApi {

    suspend fun getSearchMovieCall(): List<Movie>
}