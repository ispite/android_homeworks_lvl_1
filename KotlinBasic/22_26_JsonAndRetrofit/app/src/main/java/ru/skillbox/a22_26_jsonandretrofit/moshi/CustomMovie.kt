package ru.skillbox.a22_26_jsonandretrofit.moshi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
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
)
