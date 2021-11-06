package ru.skillbox.a16_lists_1.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.skillbox.a16_lists_1.Vehicle

class VehicleAdapter(
    private val onItemClick: (position: Int) -> Unit,
    var tabNumber: Int
) : AsyncListDifferDelegationAdapter<Vehicle>(VehiclesDiffUtilCallback()) {

    //var tabNumber: Int

    init {
        delegatesManager.addDelegate(CarAdapterDelegate(onItemClick, tabNumber) )
            .addDelegate(SelfDrivigCarAdapterDelegate(onItemClick, tabNumber))
    }

    class VehiclesDiffUtilCallback : DiffUtil.ItemCallback<Vehicle>() {
        override fun areItemsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
            return when {
                oldItem is Vehicle.Car && newItem is Vehicle.Car -> oldItem.id == newItem.id
                oldItem is Vehicle.SelfDrivingCar && newItem is Vehicle.SelfDrivingCar -> oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val TYPE_CAR = 1
        private const val TYPE_SELFDRIVINGCAR = 2
    }
}