package ru.skillbox.a16_lists_1.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.skillbox.a16_lists_1.R

abstract class BaseVehicleHolder(
    view: View,
    onItemClick: (id: Long, linkForPhoto: String, trueID: Long) -> Unit,
    onLongClickListener: (position: Long) -> Boolean
) : RecyclerView.ViewHolder(view) {

    private val brandTextView: TextView = view.findViewById(R.id.brandTextView)
    private val modelTextView: TextView = view.findViewById(R.id.modelTextView)
    private val pictureImageView: ImageView = view.findViewById(R.id.imageOfVehicleImageView)

    private var vehiclePhoto: String? = null
    private var identifier: Long? = null

    init {
        view.setOnClickListener {
            onItemClick(bindingAdapterPosition.toLong(), vehiclePhoto!!, identifier!!)
        }
        view.setOnLongClickListener {
            onLongClickListener(bindingAdapterPosition.toLong())
        }
    }

    protected fun bindMainInfo(
        id: Long,
        brand: String,
        model: String,
        image: String
    ) {
        this.identifier = id
        this.vehiclePhoto = image

        brandTextView.text = brand
        modelTextView.text = model
        Glide.with(itemView)
            .load(image)
            .placeholder(R.drawable.ic_image)
            .into(pictureImageView)
    }
}