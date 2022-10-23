package ru.skillbox.a30_34_flow.networking

import retrofit2.http.GET
import retrofit2.http.Query
import ru.skillbox.a30_34_flow.data.OmdbResponse

interface OmdbApi {

    @GET(".")
    suspend fun getSearchMovieCall(
        @Query("s") title: String,
        @Query("type") typeOfVideo: String
    ): OmdbResponse
}