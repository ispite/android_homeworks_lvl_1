package ru.skillbox.a16_lists_1.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import ru.skillbox.a16_lists_1.R
import ru.skillbox.a16_lists_1.Vehicle
import ru.skillbox.a16_lists_1.VehicleAdapter
import ru.skillbox.a16_lists_1.inflate

class VehicleAdapter_Dynamic(
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val differ = AsyncListDiffer<Vehicle>(this, VehiclesDiffUtilCallback())
    private val delegatesManager = AdapterDelegatesManager<List<Vehicle>>()

    init {
        delegatesManager.addDelegate(CarAdapterDelegate(onItemClick))
        .addDelegate(SelfDrivigCarAdapterDelegate(onItemClick))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getItemViewType(differ.currentList, position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesManager.onBindViewHolder(differ.currentList, position, holder)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun updateVehicles(newVehicles: List<Vehicle>) {
        differ.submitList(newVehicles)
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