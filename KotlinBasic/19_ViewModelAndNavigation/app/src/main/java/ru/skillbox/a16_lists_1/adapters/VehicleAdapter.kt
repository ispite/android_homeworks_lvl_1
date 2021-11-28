package ru.skillbox.a16_lists_1.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.skillbox.a16_lists_1.Vehicle


class VehicleAdapter(
    private val onItemClick: (id: Long, linkForPhoto: String, trueID: Long) -> Unit,
    private val onLongClickListener: (position: Long) -> Boolean
) : AsyncListDifferDelegationAdapter<Vehicle>(VehiclesDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(CarAdapterDelegate(onItemClick, onLongClickListener))
            .addDelegate(SelfDrivigCarAdapterDelegate(onItemClick, onLongClickListener))

    }

    fun setEmptyList() {
        differ.submitList(null)
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

}