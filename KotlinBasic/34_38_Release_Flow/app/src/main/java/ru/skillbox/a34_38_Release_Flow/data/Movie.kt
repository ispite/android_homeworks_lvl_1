package ru.skillbox.a34_38_Release_Flow.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "imdbID")
    val id: String,
    @Json(name = "Title")
    val title: String,
    @Json(name = "Type")
    val type: String,
    @Json(name = "Year")
    val year: String,
    @Json(name = "Poster")
    val poster: String
)
