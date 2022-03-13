package ru.skillbox.a221_261_jsonandretrofit.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerItemsWrapper<T>(
    val items: List<T>
)