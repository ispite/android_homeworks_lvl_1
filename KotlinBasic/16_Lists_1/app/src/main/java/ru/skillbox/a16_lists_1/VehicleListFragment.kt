package ru.skillbox.a16_lists_1

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.dialog_add_vehicle.*
import kotlinx.android.synthetic.main.fragment_vehicle_list.*
import java.text.FieldPosition

class VehicleListFragment : Fragment(R.layout.fragment_vehicle_list), NewVehicleDialogFragment.NewVehicleDialogListener {

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

//    БЫЛО
//    private var vehicleAdapter: VehicleAdapter? = null

    //    СТАЛО
    private var vehicleAdapter by AutoClearedValue<VehicleAdapter>(this)
    //vehicleAdapter = VehicleAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        initList()
        initListVehicles()
        addFAB.setOnClickListener { addVehicle() }
        addFABManual.setOnClickListener {
            NewVehicleDialogFragment()
                .show(childFragmentManager, "DIALOG")
        }
        vehicleAdapter?.updateVehicles(vehicles)
        vehicleAdapter?.notifyItemRangeInserted(0, vehicles.size)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //vehicleAdapter = null
    }

    private fun initList() {
        with(vehicleList) {
            adapter =
                CarAdapter(cars + cars + cars + cars + cars + cars + cars + cars + cars + cars)
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun initListVehicles() {
        vehicleAdapter = VehicleAdapter { position -> deleteVehicle(position) }
        with(vehicleList) {
            adapter = vehicleAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun deleteVehicle(position: Int) {
        vehicles = vehicles.filterIndexed { index, vehicle -> index != position }
        vehicleAdapter?.updateVehicles(vehicles)
        vehicleAdapter?.notifyItemRemoved(position)
    }

    private fun addVehicle() {
        val newVehicle = vehicles.random()
        vehicles = listOf(newVehicle) + vehicles
        vehicleAdapter?.updateVehicles(vehicles)
        vehicleAdapter?.notifyItemInserted(0)
        vehicleList.scrollToPosition(0)
    }

    /*fun addVehicleManual(brand: String, model: String, image: String, selfDrivingLevel: String) {
        //val newVehicle : Vehicle
        //newVehicle.
        //var newVehicleCar1 : Vehicle.Car
        val newVehicleCar1 = Vehicle.Car(brand = "asdasd", model = "sdfsdf", image = "sdfsdfsdf")
        val newVehicle = when (selfDrivingLevel.toInt()) {
            in 1..5 -> Vehicle.SelfDrivingCar(
                brand = brand,
                model = model,
                image = image,
                selfDrivingLevel.toInt()
            )
            else -> Vehicle.Car(brand = brand, model = model, image = image)
        }
        vehicles = listOf(newVehicle) + vehicles
        vehicleAdapter?.updateVehicles(vehicles)
        vehicleAdapter?.notifyItemInserted(0)
        vehicleList.scrollToPosition(0)

        *//* AlertDialog.Builder(requireContext())
                .setView(R.layout.dialog_add_vehicle)
                .setPositiveButton("ok") { _,_, ->
    //                brandEditText.text.toString()
                    Toast.makeText(requireContext(), brandEditText.text.toString(), Toast.LENGTH_SHORT).show()

    //                modelEditText.text.toString()
    //                URLEditText.text.toString()
    //                SelfDrivingLevelEditText.text.toString()
                }
                .show()*//*
    }*/

    override fun passArguments(brand: String?, model: String?, URL: String?, SDL: String?) {
        Toast.makeText(context, brand, Toast.LENGTH_SHORT).show()
    }
}