package ru.skillbox.a34_38_Release_Flow.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OmdbResponse(

    @Json(name = "Search")
    val search: List<Movie>?,

    val totalResults: String?,

    @Json(name = "Response")
    val response: String,

    @Json(name = "Error")
    val error: String?
)