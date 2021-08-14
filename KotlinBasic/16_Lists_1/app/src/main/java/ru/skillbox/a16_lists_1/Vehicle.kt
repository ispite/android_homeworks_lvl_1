package ru.skillbox.a16_lists_1

sealed class Vehicle {
    data class Car(
        val brand: String,
        val model: String,
        val image: String
    ):Vehicle()

    data class selfDrivingCar(
        val brand: String,
        val model: String,
        val image: String,
        val selfDrivingLevel: Int
    ):Vehicle()
}