package ru.skillbox.a221_261_jsonandretrofit.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteBio(val bio: String)
