package ru.skillbox.a22_26_jsonandretrofit.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.net.URL

class MovieCustomAdapter {

    @FromJson
    fun fromJson(customMovie: CustomMovie): Movie {
        return Movie(
            id = customMovie.id,
            title = customMovie.title,
            year = customMovie.year,
            scores = customMovie.scores,
            poster = URL(customMovie.poster)
        )
    }

    @JsonClass(generateAdapter = true)
    data class CustomMovie(
        @Json(name = "imdbID")
        val id: String,
        @Json(name = "Title")
        val title: String,
        @Json(name = "Year")
        val year: Int,
        //val rating: MovieRating = MovieRating.GENERAL,
        //val scores: List<Score> = emptyList()

        @Json(name = "Ratings")
        val scores: List<Score>,
        @Json(name = "Poster")
        val poster: String
    )
}