package ru.skillbox.a32_36_materialdesign.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Long,
    val title: String,
    val description: String,
    val image: String
) : Parcelable
