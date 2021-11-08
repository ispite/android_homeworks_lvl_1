package ru.skillbox.a18_permissionsanddate

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_location.*
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import android.location.Geocoder
import com.bumptech.glide.Glide
import java.util.*

class LocationAdapter(
    private val onItemClick: (position: Int) -> Unit
) : androidx.recyclerview.widget.ListAdapter<Location, LocationAdapter.Holder>(
    LocationDiffUtilCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(parent.inflate(R.layout.item_location), onItemClick)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class LocationDiffUtilCallback : DiffUtil.ItemCallback<Location>() {
        override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
            return oldItem == newItem
        }
    }

    class Holder(
        override val containerView: View,
        onItemClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
            containerView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        private val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/YY")
            .withZone(ZoneId.systemDefault())

        fun bind(location: Location) {
            placeNameTextView.text = location.address
            accuracyTextView.text = "Accuracy: " + location.accuracy
            speedTextView.text = "Speed: " + location.speed
            timeDateTextView.text = formatter.format(location.wasIn)

            Glide.with(itemView)
                .load(location.picture)
                .placeholder(R.drawable.ic_image)
                .into(imageOfLocationImageView)
        }
    }
}