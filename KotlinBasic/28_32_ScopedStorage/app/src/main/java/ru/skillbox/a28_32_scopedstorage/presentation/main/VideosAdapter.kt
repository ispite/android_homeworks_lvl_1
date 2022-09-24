package ru.skillbox.a28_32_scopedstorage.presentation.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.skillbox.a28_32_scopedstorage.R
import ru.skillbox.a28_32_scopedstorage.data.Video
import ru.skillbox.a28_32_scopedstorage.databinding.ItemVideoBinding
import ru.skillbox.a28_32_scopedstorage.utils.inflate

class VideosAdapter(
    private val onDeleteVideo: (id: Long) -> Unit
) : RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {

    private var videoList: List<Video> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val some =
            parent.inflate(ItemVideoBinding::inflate).let { VideoViewHolder(it, onDeleteVideo) }

//        val inflater = LayoutInflater.from(parent.context)
//        val binding = ItemVideoBinding.inflate(inflater, parent, false)
//        val result = VideoViewHolder(binding)

        return /*result*/ some
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

    class VideoViewHolder(
        private val binding: ItemVideoBinding,
        onDeleteVideo: (id: Long) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentVideoId: Long? = null

        init {
            binding.deleteVideoButton.setOnClickListener {
                currentVideoId?.let(onDeleteVideo)
            }
        }

        fun bind(item: Video) {
            currentVideoId = item.id
            with(binding) {
                videoTitle.text = item.name
//                videoSize.text = item.size.toString()
                videoSize.text = this.root.context.getString(R.string.video_size_string, item.size)

                Glide.with(videoThumbnail)
                    .load(item.uri)
                    .into(videoThumbnail)
            }
        }
    }
}