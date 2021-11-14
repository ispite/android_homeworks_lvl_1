package ru.skillbox.a16_lists_1.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.skillbox.a16_lists_1.R

abstract class BaseVehicleHolder(
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
}