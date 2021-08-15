package ru.skillbox.a16_lists_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class VehicleAdapter(
    private val onItemClick: (position: Int) -> Unit
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var vehicles: List<Vehicle> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemViewType(position: Int): Int {
        return when(vehicles[position]) {
            is Vehicle.Car -> TYPE_CAR
            is Vehicle.SelfDrivingCar -> TYPE_SELFDRIVINGCAR
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = vehicles.size

    fun updateVehicles(newVehicles: List<Vehicle>){
        vehicles = newVehicles
    }

    abstract class BaseVehicleHolder(
        view: View,
        onItemClick: (position: Int) -> Unit
    ):RecyclerView.ViewHolder(view) {
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
                .placeholder(R.drawable.ic_baseline_image)
                .into(pictureImageView)
        }
    }

    companion object {
        private const val TYPE_CAR = 1
        private const val TYPE_SELFDRIVINGCAR = 2
    }
}