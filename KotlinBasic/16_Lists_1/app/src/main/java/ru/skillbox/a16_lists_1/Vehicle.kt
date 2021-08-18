package ru.skillbox.a16_lists_1

sealed class Vehicle {
    data class Car(
        var brand: String,
        val model: String,
        val image: String
    ) : Vehicle()

    data class SelfDrivingCar(
        val brand: String,
        val model: String,
        val image: String,
        val selfDrivingLevel: Int
    ) : Vehicle()
}