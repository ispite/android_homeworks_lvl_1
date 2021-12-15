package ru.skillbox.a22_26_jsonandretrofit.moshi

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.net.URL

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name = "imdbID")
    val id: String,
    @Json(name = "Title")
    val title: String,
    @Json(name = "Year")
    val year: Int,

    @Json(name = "Ratings")
    val scores: List<Score>,
    @Json(name = "Poster")
    val poster: URL
)

/*enum class Rating {
    QWE, ASD, ZXC
}*/

enum class Rating() {
    IMDB,
    RT,
    MC
}

@JsonClass(generateAdapter = true)
data class Score(
    @Json(name = "Source")
    val source: String,
    @Json(name = "Value")
    val value: String
)
