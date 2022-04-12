package ru.skillbox.a25_29_contentprovider.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Course(
    val id: Long,
    val title: String,
    val duration: Long
)
