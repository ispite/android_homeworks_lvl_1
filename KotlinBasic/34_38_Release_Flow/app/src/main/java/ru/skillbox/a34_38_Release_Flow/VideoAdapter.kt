package ru.skillbox.a34_38_Release_Flow

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.skillbox.a34_38_Release_Flow.data.Movie
import ru.skillbox.a34_38_Release_Flow.databinding.ItemMovieBinding
import ru.skillbox.a34_38_Release_Flow.utils.inflate

class VideoAdapter : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private var videoList: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(parent.inflate(ItemMovieBinding::inflate))
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val currentVideo = videoList[position]
        holder.bind(currentVideo)
    }

    override fun getItemCount(): Int = videoList.size

    fun submitList(videoList: List<Movie>) {
        this.videoList = videoList
        notifyDataSetChanged()
    }

    class VideoViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie) {
            with(binding) {
                videoTitle.text = item.title
                videoType.text = item.type
                videoYear.text = item.year

                Glide.with(itemView)
                    .load(item.poster)
                    .placeholder(R.drawable.ic_image)
                    .into(videoPoster)
            }
        }
    }
}