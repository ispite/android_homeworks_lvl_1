package ru.skillbox.a22_26_jsonandretrofit.moshi

data class RemoteMovie(
    val id: String,
    val title: String,
    val type: String,
    val year: String,
    val poster: String
)
