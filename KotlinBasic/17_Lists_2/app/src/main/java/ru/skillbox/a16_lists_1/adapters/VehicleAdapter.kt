package ru.skillbox.a16_lists_1.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.skillbox.a16_lists_1.Vehicle

class VehicleAdapter(
    private val onItemClick: (position: Int) -> Unit
) : AsyncListDifferDelegationAdapter<Vehicle>(VehiclesDiffUtilCallback()) {

     private var tabNumber : Int = 0
         get() {
            TODO()
        }
        set(value) {
            if (value in 0..2) field = value
        }

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

    companion object MyObject{
        private const val TYPE_CAR = 1
        private const val TYPE_SELFDRIVINGCAR = 2

        fun VehicleAdapter.newInstance(passedTabNumber:Int):VehicleAdapter {
            tabNumber = passedTabNumber
            return VehicleAdapter
        }

        fun newInstance2(passedTabNumber:Int):VehicleAdapter {
            MyObject.tabNumber = passedTabNumber
            return VehicleAdapter
            }
    }
}