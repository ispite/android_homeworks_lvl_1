package ru.skillbox.a16_lists_1.adapters

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.skillbox.a16_lists_1.R

abstract class BaseVehicleHolder(
    view: View,
    onItemClick: (position: Int) -> Unit
) : RecyclerView.ViewHolder(view) {
    private val pictureImageView: ImageView = view.findViewById(R.id.imageView)

    init {
        view.setOnClickListener {
            onItemClick(adapterPosition)
        }
    }

    protected fun bindMainInfo(

        image: String
    ) {
        Glide.with(itemView)
            .load(image)
            .placeholder(R.drawable.ic_image)
            .into(pictureImageView)
    }
}