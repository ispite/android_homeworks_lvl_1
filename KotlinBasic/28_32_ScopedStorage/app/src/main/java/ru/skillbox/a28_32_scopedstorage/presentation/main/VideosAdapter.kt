package ru.skillbox.a28_32_scopedstorage.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.a28_32_scopedstorage.data.Video
import ru.skillbox.a28_32_scopedstorage.databinding.ItemVideoBinding
import ru.skillbox.a28_32_scopedstorage.utils.inflate

class VideosAdapter : RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {

    private var videoList: List<Video> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val some = parent.inflate(ItemVideoBinding::inflate).let { VideoViewHolder(it) }

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVideoBinding.inflate(inflater, parent, false)
        val result = VideoViewHolder(binding)

        return result //some
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val currentVideo = videoList[position]
        holder.bind(currentVideo)
    }

    override fun getItemCount(): Int = videoList.size

    fun submitList(videoList: List<Video>) {
        this.videoList = videoList
        notifyDataSetChanged()
    }

    class VideoViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentVideoId: Long? = null

        fun bind(item: Video) {
            currentVideoId = item.id
            with(binding) {
                someText.text = item.name
            }
        }
    }
}