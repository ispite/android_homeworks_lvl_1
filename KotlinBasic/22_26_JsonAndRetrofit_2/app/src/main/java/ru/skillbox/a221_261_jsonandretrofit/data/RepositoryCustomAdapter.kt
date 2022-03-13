package ru.skillbox.a221_261_jsonandretrofit.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

class RepositoryCustomAdapter {

    @JsonClass(generateAdapter = true)
    data class Owner(
        val login: String
    )

    @JsonClass(generateAdapter = true)
    data class RepositoryCustom(
        val id: Long,
        val name: String,
        val description: String?,
        @Json(name = "owner")
        val ownerLogin: Owner
    )
}