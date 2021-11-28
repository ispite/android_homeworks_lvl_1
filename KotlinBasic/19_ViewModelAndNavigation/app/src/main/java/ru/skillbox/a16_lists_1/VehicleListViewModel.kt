package ru.skillbox.a16_lists_1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VehicleListViewModel : ViewModel() {

    private val repository = VehicleRepository()

    private val vehicleLiveData = MutableLiveData<List<Vehicle>>(repository.generateVehicles(2))
    val vehicles: LiveData<List<Vehicle>>
        get() = vehicleLiveData

    private val showToastLiveData = SingleLiveEvent<Unit>()
    val showToast: LiveData<Unit>
        get() = showToastLiveData

    private val showToastDeleteItem = SingleLiveEvent<Int>()
    val showDeleteToast: LiveData<Int>
        get() = showToastDeleteItem

    fun addVehicle() {
        val newVehicle = repository.createVehicle()
        val updatedList = listOf(newVehicle) + vehicleLiveData.value.orEmpty()
        vehicleLiveData.postValue(updatedList)
        showToastLiveData.postValue(Unit)
    }

    fun deleteVehicle(position: Int) {
        vehicleLiveData.postValue(
            repository.deleteVehicle(
                vehicleLiveData.value.orEmpty(),
                position
            )
        )
        showToastDeleteItem.postValue(position)
    }

    fun generateVehicles(count: Int) {
        val newVehicles = repository.generateVehicles(count)
        val updatedList = vehicleLiveData.value.orEmpty() + (newVehicles)
        vehicleLiveData.postValue(updatedList)
    }

    fun addVehicleManual(
        id: Long,
        brand: String?,
        model: String?,
        image: String?,
        selfDrivingLevel: String?
    ) {
        val newVehicle = repository.addVehicleManual(id, brand, model, image, selfDrivingLevel)
        val updatedList = listOf(newVehicle) + vehicleLiveData.value.orEmpty()
        vehicleLiveData.postValue(updatedList)
    }
}