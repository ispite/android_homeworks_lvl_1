package ru.skillbox.a30_34_flow.networking

import retrofit2.http.GET
import retrofit2.http.Query
import ru.skillbox.a30_34_flow.data.Movie

interface OmdbApi {

    @GET
    suspend fun getSearchMovieCall(
        @Query("s") title: String
    ): List<Movie>
}