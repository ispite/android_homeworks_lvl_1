package ru.skillbox.a16_lists_1

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_vehicle_list.*

class VehicleListFragment : Fragment(R.layout.fragment_vehicle_list),
    NewVehicleDialogFragment.NewVehicleDialogListener {

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
    //    БЫЛО
    //    private var vehicleAdapter: VehicleAdapter? = null

    //    СТАЛО
    private var vehicleAdapter by AutoClearedValue<VehicleAdapter>(this)
    //vehicleAdapter = VehicleAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListVehicles()
        addFAB.setOnClickListener { addVehicle() }
        addFABManual.setOnClickListener {
            NewVehicleDialogFragment()
                .show(childFragmentManager, "DIALOG")
        }
        vehicleAdapter.updateVehicles(vehicles)
        vehicleAdapter.notifyItemRangeInserted(0, vehicles.size)
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
        vehicleAdapter.updateVehicles(vehicles)
        vehicleAdapter.notifyItemRemoved(position)
        if (vehicles.isEmpty()) {
            emptyListNotification.visibility = View.VISIBLE
        }
    }

    private fun addVehicle() {
        val newVehicle = vehicles.random()
        vehicles = listOf(newVehicle) + vehicles
        vehicleAdapter.updateVehicles(vehicles)
        vehicleAdapter.notifyItemInserted(0)
        vehicleList.scrollToPosition(0)
    }

    private fun addVehicleManual(
        brand: String?,
        model: String?,
        image: String?,
        selfDrivingLevel: String?
    ) {
        val newVehicle = when (selfDrivingLevel?.toIntOrNull()) {
            in 1..5 -> Vehicle.SelfDrivingCar(
                brand = brand!!,
                model = model!!,
                image = image!!,
                selfDrivingLevel!!.toInt()
            )
            else -> Vehicle.Car(brand = brand!!, model = model!!, image = image!!)
        }
        vehicles = listOf(newVehicle) + vehicles
        vehicleAdapter.updateVehicles(vehicles)
        vehicleAdapter.notifyItemInserted(0)
        vehicleList.scrollToPosition(0)
        if (vehicles.isNotEmpty()) {
            emptyListNotification.visibility = View.GONE
        }
    }

    override fun passArguments(brand: String?, model: String?, URL: String?, SDL: String?) {
        Toast.makeText(context, brand, Toast.LENGTH_SHORT).show()
        addVehicleManual(brand, model, URL, SDL)
    }
}