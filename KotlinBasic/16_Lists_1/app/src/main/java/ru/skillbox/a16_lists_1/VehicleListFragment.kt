package ru.skillbox.a16_lists_1

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_vehicle_list.*

class VehicleListFragment:Fragment(R.layout.fragment_vehicle_list) {

    private var vehicles = listOf(
        Vehicle.Car(
            brand = "Volkswagen",
            model = "Passat b6",
            image = "https://ekb.explorer-russia.ru/gallery/auto/modification/3615.jpg"
        ),
        Vehicle.SelfDrivingCar(
            brand = "Volvo",
            model = "S60",
            image = "https://autoiwc.ru/images/volvo/volvo-s60.jpg",
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

    private val cars = listOf(
        Car(
            brand = "Volkswagen",
            model = "Passat b6",
            image = "https://ekb.explorer-russia.ru/gallery/auto/modification/3615.jpg"
        ),
        Car(
            brand = "BMW",
            model = "3 series",
            image = "https://www.carpixel.net/w/e42a7b718cbd375a65f82c97de695dd3/bmw-3-series-wallpaper-hd-81853.jpg"
        )
    )


    private var vehicleAdapter: VehicleAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        initList()
        initListVehicles()
        addFAB.setOnClickListener { addVehicle() }
        vehicleAdapter?.updateVehicles(vehicles)
    }

    private fun initList(){
        //vehicleAdapter = VehicleAdapter{position -> {} }
        with(vehicleList) {
            adapter = CarAdapter(cars + cars + cars + cars + cars + cars + cars + cars + cars + cars)
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun initListVehicles(){
        vehicleAdapter = VehicleAdapter{position -> {} }
        with(vehicleList) {
            adapter = vehicleAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun addVehicle() {
        val newVehicle = vehicles.random()
        vehicles = listOf(newVehicle) + vehicles
        vehicleAdapter?.updateVehicles(vehicles)
    }
}