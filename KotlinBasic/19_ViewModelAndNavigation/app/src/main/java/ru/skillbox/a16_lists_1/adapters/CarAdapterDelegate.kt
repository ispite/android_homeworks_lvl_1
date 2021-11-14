package ru.skillbox.a16_lists_1.adapters

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.skillbox.a16_lists_1.R
import ru.skillbox.a16_lists_1.Vehicle
import ru.skillbox.a16_lists_1.inflate

class CarAdapterDelegate(
    private val onItemClick: (position: Int) -> Unit,
) : AbsListItemAdapterDelegate<Vehicle.Car, Vehicle, CarAdapterDelegate.CarHolder>() {

    override fun isForViewType(item: Vehicle, items: MutableList<Vehicle>, position: Int): Boolean {
        return item is Vehicle.Car
    }

    override fun onCreateViewHolder(parent: ViewGroup): CarHolder {
        return CarHolder(parent.inflate(R.layout.item_car), onItemClick)
    }

    override fun onBindViewHolder(
        item: Vehicle.Car,
        holder: CarHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class CarHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseVehicleHolder(view, onItemClick) {

        fun bind(vehicle: Vehicle.Car) {
            bindMainInfo(vehicle.brand, vehicle.model, vehicle.image)
        }
    }
}