package ru.skillbox.a16_lists_1

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class Vehicle : Parcelable {
    @Parcelize
    data class Car(
        val id: Long,
        val brand: String,
        val model: String,
        val image: String
    ) : Vehicle()

    @Parcelize
    data class SelfDrivingCar(
        val id: Long,
        val brand: String,
        val model: String,
        val image: String,
        val selfDrivingLevel: Int
    ) : Vehicle()
}