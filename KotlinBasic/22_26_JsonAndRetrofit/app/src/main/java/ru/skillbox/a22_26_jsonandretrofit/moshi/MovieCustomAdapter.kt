package ru.skillbox.a22_26_jsonandretrofit.moshi

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.net.URL

class MovieCustomAdapter {

    @FromJson
    fun fromJson(customMovie: CustomMovie): Movie {
        return Movie(
            title = customMovie.title,
            year = customMovie.year,
            ageRating = customMovie.ageRating,
            genre = customMovie.genre,
            poster = URL(customMovie.poster),
            scores = customMovie.scores.associateBy({ it.source }, { it.value })
        )
    }

    @ToJson
    fun toJson(movie: Movie): CustomMovie {
        return CustomMovie(
            title = movie.title,
            year = movie.year,
            ageRating = movie.ageRating,
            genre = movie.genre,
            poster = movie.poster.toString(),
            scores = movie.scores.map { Score(it.key, it.value) }
        )
    }

/*    @JsonClass(generateAdapter = true)
    data class CustomMovie(
        @Json(name = "Title")
        val title: String,
        @Json(name = "Year")
        val year: Int,
        @Json(name = "Genre")
        val genre: String,
        @Json(name = "Poster")
        val poster: String,
        @Json(name = "Ratings")
        val scores: List<Score>
    )*/
}