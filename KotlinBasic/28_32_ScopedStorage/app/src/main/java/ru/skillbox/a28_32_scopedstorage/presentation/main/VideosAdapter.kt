package ru.skillbox.a28_32_scopedstorage.presentation.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.skillbox.a28_32_scopedstorage.R
import ru.skillbox.a28_32_scopedstorage.data.Video
import ru.skillbox.a28_32_scopedstorage.databinding.ItemVideoBinding
import ru.skillbox.a28_32_scopedstorage.utils.haveR
import ru.skillbox.a28_32_scopedstorage.utils.inflate

class VideosAdapter(
    private val onDeleteVideo: (id: Long) -> Unit,
    private val onMoveToTrash: (id: Long) -> Unit,
    private val onMarkAsFavorite: (id: Long, favoriteState: Boolean) -> Unit
) : RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {

    private var videoList: List<Video> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val some =
            parent.inflate(ItemVideoBinding::inflate)
                .let { VideoViewHolder(it, onDeleteVideo, onMoveToTrash, onMarkAsFavorite) }

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
        onDeleteVideo: (id: Long) -> Unit,
        onMoveToTrash: (id: Long) -> Unit,
        onMarkAsFavorite: (id: Long, favoriteState: Boolean) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var currentVideoId: Long? = null
        private var currentFavoriteState: Boolean? = null

        init {
            binding.deleteVideoButton.setOnClickListener {
                currentVideoId?.let(onDeleteVideo)
            }
            if (haveR()) {
                binding.moveToTrashButton.visibility = View.VISIBLE
                binding.moveToTrashButton.setOnClickListener {
                    currentVideoId?.let(onMoveToTrash)
                }

                binding.favoriteButton.visibility = View.VISIBLE
                binding.favoriteButton.setOnClickListener {
//                    currentVideoId?.let(onMarkAsFavorite)
                    if (currentVideoId != null && currentFavoriteState != null) {
                        onMarkAsFavorite(currentVideoId!!, currentFavoriteState!!)
                    }
                }
//                if (currentVideoId)
            }
        }

        fun bind(item: Video) {
            currentVideoId = item.id
            with(binding) {
                videoTitle.text = item.name
                videoSize.text = this.root.context.getString(R.string.video_size_string, item.size)
                if (item.isFavorite == 1) {
                    favoriteButton.setImageResource(R.drawable.ic_favorite)
                    currentFavoriteState = true
                } else {
                    favoriteButton.setImageResource(R.drawable.ic_favorite_border)
                    currentFavoriteState = false
                }

                Glide.with(videoThumbnail)
                    .load(item.uri)
                    .into(videoThumbnail)
            }
        }
    }
}