package ru.skillbox.a16_lists_1

import androidx.lifecycle.ViewModel
import kotlin.random.Random

class VehicleListViewModel : ViewModel() {

    private val repository = VehicleRepository()
    private var vehicles = repository.generateVehicles(6)

    fun addVehicle() {
        val newVehicle = repository.createVehicle()
/*        val newVehicle = vehicles.random()
        //vehicles = listOf(newVehicle) + vehicles*/
        vehicles = (listOf(newVehicle) + vehicles) /*as ArrayList<Vehicle>*/
    }

    fun deleteVehicle(position: Int) {
        vehicles = repository.deleteVehicle(vehicles, position)
/*        vehicles =
            vehicles.filterIndexed { index, vehicle -> index != position } as ArrayList<Vehicle>*/
    }

    fun generateVehicles(count: Int): List<Vehicle> {
        return repository.generateVehicles(count)
    }

    fun getVehicleList() = vehicles

    fun addVehicleManual(
        id: Long,
        brand: String?,
        model: String?,
        image: String?,
        selfDrivingLevel: String?
    ) {
        val newVehicle = repository.addVehicleManual(id, brand, model, image, selfDrivingLevel)
        vehicles = (listOf(newVehicle) + vehicles) /*as ArrayList<Vehicle>*/
    }
}