package ru.skillbox.a16_lists_1.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.skillbox.a16_lists_1.Vehicle

class VehicleAdapter(
    private val onItemClick: (position: Int) -> Unit
) : AsyncListDifferDelegationAdapter<Vehicle>(VehiclesDiffUtilCallback()) {

    private var vehicles: List<Vehicle> = emptyList()

    init {
        delegatesManager.addDelegate(CarAdapterDelegate(onItemClick))
            .addDelegate(SelfDrivigCarAdapterDelegate(onItemClick))
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

/*    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_CAR -> CarHolder(parent.inflate(R.layout.item_car), onItemClick)
            TYPE_SELFDRIVINGCAR -> SelfDrivingCarHolder(
                parent.inflate(R.layout.item_selfdrivingcar),
                onItemClick
            )
            else -> error("INCORRECT VIEWTYPE=$viewType")
        }
    }*/

/*    override fun getItemViewType(position: Int): Int {
        return when (vehicles[position]) {
            is Vehicle.Car -> TYPE_CAR
            is Vehicle.SelfDrivingCar -> TYPE_SELFDRIVINGCAR
        }
    }*/

/*    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CarHolder -> {
                val vehicle = vehicles[position].let { it as? Vehicle.Car }
                    ?: error("Vehicle at position =$position is not a CAR")
                holder.bind(vehicle)
            }
            is SelfDrivingCarHolder -> {
                val vehicle = vehicles[position].let { it as? Vehicle.SelfDrivingCar }
                    ?: error("Vehicle at position =$position is not a Self Driving CAR")
                holder.bind(vehicle)
            }
            else -> error("Incorrect view holder = $holder")
        }
    }*/

/*    override fun getItemCount(): Int = vehicles.size*/

/*    fun updateVehicles(newVehicles: List<Vehicle>) {
        vehicles = newVehicles
    }*/

/*    abstract class BaseVehicleHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val brandTextView: TextView = view.findViewById(R.id.brandTextView)
        private val modelTextView: TextView = view.findViewById(R.id.modelTextView)
        private val pictureImageView: ImageView = view.findViewById(R.id.imageOfVehicleImageView)

        init {
            view.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        protected fun bindMainInfo(
            brand: String,
            model: String,
            image: String
        ) {
            brandTextView.text = brand
            modelTextView.text = model

            Glide.with(itemView)
                .load(image)
                .placeholder(R.drawable.ic_image)
                .into(pictureImageView)
        }
    }*/

/*    class CarHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseVehicleHolder(view, onItemClick) {

        fun bind(vehicle: Vehicle.Car) {
            bindMainInfo(vehicle.brand, vehicle.model, vehicle.image)
        }
    }*/

/*    class SelfDrivingCarHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ) : BaseVehicleHolder(view, onItemClick) {
        private val selfDrivingLevelView: TextView =
            view.findViewById(R.id.selfDrivingLevelTextView)

        fun bind(vehicle: Vehicle.SelfDrivingCar) {
            bindMainInfo(vehicle.brand, vehicle.model, vehicle.image)
            selfDrivingLevelView.text = "Уровень автопилота: ${vehicle.selfDrivingLevel.toString()}"
        }
    }*/

/*    companion object {
        private const val TYPE_CAR = 1
        private const val TYPE_SELFDRIVINGCAR = 2
    }*/
}