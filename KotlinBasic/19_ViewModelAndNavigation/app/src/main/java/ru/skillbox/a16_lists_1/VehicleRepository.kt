package ru.skillbox.a16_lists_1

import kotlin.random.Random

class VehicleRepository {

    fun generateVehicles(count: Int): List<Vehicle> {
        val brands = listOf(
            "Volkswagen",
            "Volvo",
            "BMW",
            "Tesla",
            "Audi",
            "Lamborghini"
        )

        val models = listOf(
            "Passat B6",
            "S60",
            "3 series",
            "Model 3",
            "A8",
            "Aventador"
        )

        val images = listOf(
            "https://ekb.explorer-russia.ru/gallery/auto/modification/3615.jpg",
            "https://autoiwc.ru/images/volvo/volvo-s60.jpg",
            "https://www.carpixel.net/w/e42a7b718cbd375a65f82c97de695dd3/bmw-3-series-wallpaper-hd-81853.jpg",
            "https://pbs.twimg.com/media/DytoztBWoAAbR2r.jpg",
            "https://s0.rbk.ru/v6_top_pics/resized/1440xH/media/img/3/64/754788601082643.jpeg",
            "https://topgearrussia.ru/data/topgear/upload/2012-08/23/image-45f06bb6.jpg"
        )

        return (0 until count).map {
            val id = Random.nextLong(1, Long.MAX_VALUE)
            val brand = brands.random()
            val model = models.random()
            val image = images.random()
            val selfDrivingLevel = Random.nextInt(1, 4)
            val isSelfDrivingCar = Random.nextBoolean()

            if (isSelfDrivingCar) {
                Vehicle.SelfDrivingCar(
                    id = id,
                    brand = brand,
                    model = model,
                    image = image,
                    selfDrivingLevel = selfDrivingLevel
                )
            } else {
                Vehicle.Car(
                    id = id,
                    brand = brand,
                    model = model,
                    image = image
                )
            }
        }
    }

    fun createVehicle(): Vehicle {
        return generateVehicles(1).first()
    }

    fun deleteVehicle(vehicles: List<Vehicle>, position: Int): List<Vehicle> {
        return vehicles.filterIndexed { index, vehicle -> index != position }
    }

    fun addVehicleManual(
        id: Long,
        brand: String?,
        model: String?,
        image: String?,
        selfDrivingLevel: String?
    ): Vehicle {
        return when (selfDrivingLevel?.toIntOrNull()) {
            in 1..5 -> Vehicle.SelfDrivingCar(
                id = id,
                brand = brand!!,
                model = model!!,
                image = image!!,
                selfDrivingLevel!!.toInt()
            )
            else -> Vehicle.Car(id = id, brand = brand!!, model = model!!, image = image!!)
        }
    }
}