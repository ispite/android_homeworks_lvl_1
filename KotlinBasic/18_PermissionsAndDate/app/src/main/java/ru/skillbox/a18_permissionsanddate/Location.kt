package ru.skillbox.a18_permissionsanddate

import org.threeten.bp.Instant

data class Location(
    val id: Long,
    val address: String,
    val picture: String,
    val accuracy: String,
    val speed: String,
    val wasIn: Instant
)