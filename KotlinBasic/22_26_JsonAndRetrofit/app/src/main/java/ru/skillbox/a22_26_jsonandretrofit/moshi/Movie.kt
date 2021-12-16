package ru.skillbox.a22_26_jsonandretrofit.moshi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.net.URL

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "Title")
    val title: String,
    @Json(name = "Year")
    val year: Int,
    @Json(name = "Rated")
    val ageRating: AgeRating,
    @Json(name = "Genre")
    val genre: String,
    @Json(name = "Poster")
    val poster: URL,
    @Json(name = "Ratings")
    val scores: List<Score>,
)

enum class AgeRating {
    @Json(name = "G")
    GENERAL,
    PG,
    @Json(name = "PG-13")
    PG_13,
    R,
    @Json(name = "NC-17")
    NC_17
}

@JsonClass(generateAdapter = true)
data class Score(
    @Json(name = "Source")
    val source: String,
    @Json(name = "Value")
    val value: String
)
