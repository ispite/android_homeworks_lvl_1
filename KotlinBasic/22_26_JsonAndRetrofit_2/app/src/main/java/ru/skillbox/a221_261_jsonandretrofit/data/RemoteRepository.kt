package ru.skillbox.a221_261_jsonandretrofit.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteRepository(
    val id: Long,
    val name: String,
    val description: String?,
    @Json(name = "owner")
    val login: RemoteOwner
)
