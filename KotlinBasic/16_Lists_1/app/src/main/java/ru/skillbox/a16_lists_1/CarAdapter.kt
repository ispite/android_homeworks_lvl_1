package ru.skillbox.a16_lists_1

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CarAdapter(
    private val cars: List<Car>
) : RecyclerView.Adapter<CarAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflate(R.layout.item_car))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val car = cars[position]
        holder.bind(car)
    }

    override fun getItemCount(): Int = cars.size

    class Holder(view: View) : RecyclerView.ViewHolder(view) {

        private val brandTextView: TextView = view.findViewById(R.id.brandTextView)
        private val modelTextView: TextView = view.findViewById(R.id.modelTextView)
        private val pictureImageView: ImageView = view.findViewById(R.id.imageOfVehicleImageView)

        fun bind(car: Car) {
            brandTextView.text = car.brand
            modelTextView.text = car.model

            Glide.with(itemView)
                .load(car.image)
                .placeholder(R.drawable.ic_image)
                .into(pictureImageView)
        }
    }

}