package ru.skillbox.a16_lists_1.adapters

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.skillbox.a16_lists_1.R
import ru.skillbox.a16_lists_1.Vehicle
import ru.skillbox.a16_lists_1.inflate

class SelfDrivigCarAdapterDelegate(
    private val onItemClick: (position: Int) -> Unit
): AbsListItemAdapterDelegate<Vehicle.SelfDrivingCar, Vehicle, SelfDrivigCarAdapterDelegate.SelfDrivingCarHolder>(){

    override fun isForViewType(item: Vehicle, items: MutableList<Vehicle>, position: Int): Boolean {
        return item is Vehicle.SelfDrivingCar
    }

    override fun onCreateViewHolder(parent: ViewGroup): SelfDrivingCarHolder {
        return SelfDrivingCarHolder(
            parent.inflate(R.layout.item_car_image),
            onItemClick
        )
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
/*        private val selfDrivingLevelView: TextView =
            view.findViewById(R.id.selfDrivingLevelTextView)*/

        fun bind(vehicle: Vehicle.SelfDrivingCar) {
            bindMainInfo(/*vehicle.brand, vehicle.model,*/ vehicle.image)
/*            selfDrivingLevelView.text = "Уровень автопилота: ${vehicle.selfDrivingLevel.toString()}"*/
        }
    }

}