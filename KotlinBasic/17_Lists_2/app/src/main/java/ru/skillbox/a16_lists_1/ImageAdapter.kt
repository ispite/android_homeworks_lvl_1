package ru.skillbox.a16_lists_1

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_car_image.*
import kotlinx.android.synthetic.main.item_car_image.view.*

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private var images: List<String> = emptyList()
    private var listItem: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            parent.inflate(
                listItem
            )
        )
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    fun setImages(newImages: List<String>) {
        images = newImages
        notifyDataSetChanged()
    }

    fun setListItem(@LayoutRes listItemFun:  Int) {
        listItem = listItemFun
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        //val image = containerView.findViewById<ImageView>(R.id.imageView)
        //val image = itemView.imageView

        fun bind(imageUrl: String) {
            Glide.with(itemView)
                .load(imageUrl)
                .placeholder(R.drawable.ic_image)
                .into(imageView)
        }
    }
}