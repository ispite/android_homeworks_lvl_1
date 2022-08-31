package ru.skillbox.a28_32_scopedstorage.presentation.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.a28_32_scopedstorage.data.Video
import ru.skillbox.a28_32_scopedstorage.databinding.ItemVideoBinding

class VideosAdapter : RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {

    private var videoList: List<Video> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

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