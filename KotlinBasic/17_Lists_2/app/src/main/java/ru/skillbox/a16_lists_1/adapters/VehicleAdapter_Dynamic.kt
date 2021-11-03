package ru.skillbox.a16_lists_1.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.skillbox.a16_lists_1.R
import ru.skillbox.a16_lists_1.Vehicle
import ru.skillbox.a16_lists_1.VehicleAdapter
import ru.skillbox.a16_lists_1.inflate

class VehicleAdapter_Dynamic(
    private val onItemClick: (position: Int) -> Unit
) : AsyncListDifferDelegationAdapter<Vehicle>(VehiclesDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(CarAdapterDelegate(onItemClick))
        .addDelegate(SelfDrivigCarAdapterDelegate(onItemClick))
    }

    class VehiclesDiffUtilCallback: DiffUtil.ItemCallback<Vehicle>() {
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