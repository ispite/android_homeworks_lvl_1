package ru.skillbox.a16_lists_1.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.skillbox.a16_lists_1.R
import ru.skillbox.a16_lists_1.Vehicle
import ru.skillbox.a16_lists_1.inflate

class SelfDrivigCarAdapterDelegate(
    private val onItemClick: (id: Long, linkForPhoto: String, trueID: Long) -> Unit,
    private val onLongClickListener: (position: Long) -> Boolean
) : AbsListItemAdapterDelegate<Vehicle.SelfDrivingCar, Vehicle, SelfDrivigCarAdapterDelegate.SelfDrivingCarHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): SelfDrivingCarHolder {
        return SelfDrivingCarHolder(
            parent.inflate(R.layout.item_selfdrivingcar),
            onItemClick,
            onLongClickListener
        )
    }

    override fun isForViewType(item: Vehicle, items: MutableList<Vehicle>, position: Int): Boolean {
        return item is Vehicle.SelfDrivingCar
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
        onItemClick: (id: Long, linkForPhoto: String, trueID: Long) -> Unit,
        onLongClickListener: (position: Long) -> Boolean
    ) : BaseVehicleHolder(view, onItemClick, onLongClickListener) {

        private val SDL: TextView = view.findViewById(R.id.selfDrivingLevelTextView)

        fun bind(vehicle: Vehicle.SelfDrivingCar) {
            bindMainInfo(vehicle.id, vehicle.brand, vehicle.model, vehicle.image)
            SDL.text = vehicle.selfDrivingLevel.toString()
        }

    }
}