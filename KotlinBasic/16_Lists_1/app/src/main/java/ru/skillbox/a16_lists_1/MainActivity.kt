package ru.skillbox.a16_lists_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//https://ekb.explorer-russia.ru/gallery/auto/modification/3615.jpg
//http://autodd.b-cdn.net/wp-content/uploads/2019/07/volvo-s60_13.jpg
//https://www.carpixel.net/w/e42a7b718cbd375a65f82c97de695dd3/bmw-3-series-wallpaper-hd-81853.jpg
//https://pbs.twimg.com/media/DytoztBWoAAbR2r.jpg

class MainActivity : AppCompatActivity() {

    private val vehicles = listOf(
        Vehicle.Car(
            brand = "Volkswagen",
            model = "Passat b6",
            image = "https://ekb.explorer-russia.ru/gallery/auto/modification/3615.jpg"
        ),
        Vehicle.SelfDrivingCar(
            brand = "Volvo",
            model = "S60",
            image = "http://autodd.b-cdn.net/wp-content/uploads/2019/07/volvo-s60_13.jpg",
            selfDrivingLevel = 4
        ),
        Vehicle.Car(
            brand = "BMW",
            model = "3 series",
            image = "https://www.carpixel.net/w/e42a7b718cbd375a65f82c97de695dd3/bmw-3-series-wallpaper-hd-81853.jpg"
        ),
        Vehicle.SelfDrivingCar(
            brand = "Tesla",
            model = "Model 3",
            image = "https://pbs.twimg.com/media/DytoztBWoAAbR2r.jpg",
            selfDrivingLevel = 4
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}