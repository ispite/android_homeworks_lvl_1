package ru.skillbox.a18_permissionsanddate

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.Instant

@Parcelize
data class Location(
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val picture: String,
    val accuracy: String,
    val speed: String,
    var wasIn: Instant
) : Parcelable