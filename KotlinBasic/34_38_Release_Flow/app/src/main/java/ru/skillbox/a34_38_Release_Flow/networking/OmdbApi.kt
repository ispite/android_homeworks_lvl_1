package ru.skillbox.a34_38_Release_Flow.networking

import retrofit2.http.GET
import retrofit2.http.Query
import ru.skillbox.a34_38_Release_Flow.data.OmdbResponse

interface OmdbApi {

    @GET(".")
    suspend fun getSearchMovieCall(
        @Query("s") title: String,
        @Query("type") typeOfVideo: String
    ): OmdbResponse
}