package ru.skillbox.a16_lists_1.adapters

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.skillbox.a16_lists_1.R
import ru.skillbox.a16_lists_1.Vehicle
import ru.skillbox.a16_lists_1.inflate

class SelfDrivigCarAdapterDelegate(
    private val onItemClick: (position: Int) -> Unit,
    var tabNumber: Int
) : AbsListItemAdapterDelegate<Vehicle.SelfDrivingCar, Vehicle, SelfDrivigCarAdapterDelegate.SelfDrivingCarHolder>() {

    //var tabNumber: Int

    override fun isForViewType(item: Vehicle, items: MutableList<Vehicle>, position: Int): Boolean {
        return item is Vehicle.SelfDrivingCar
    }

    override fun onCreateViewHolder(parent: ViewGroup): SelfDrivingCarHolder {
        return if (tabNumber==2) {
            SelfDrivingCarHolder(parent.inflate(R.layout.item_car_image_2), onItemClick)
        } else SelfDrivingCarHolder(parent.inflate(R.layout.item_car_image), onItemClick)
    }

    override fun onBindViewHolder(
        item: Vehicle.SelfDrivingCar,
        holder: SelfDrivingCarHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class SelfDrivingCarHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseVehicleHolder(view, onItemClick) {


        fun bind(vehicle: Vehicle.SelfDrivingCar) {
            bindMainInfo(vehicle.image)
        }
    }

}