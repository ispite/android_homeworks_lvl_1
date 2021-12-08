package ru.skillbox.a21_networking.movie_search

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.*

import ru.skillbox.a21_networking.R
import ru.skillbox.a21_networking.utils.inflate

class MovieSearchDelegateAdapter:AbsListItemAdapterDelegate<RemoteMovie, RemoteMovie, MovieSearchDelegateAdapter.Holder>() {

    override fun isForViewType(
        item: RemoteMovie,
        items: MutableList<RemoteMovie>,
        position: Int
    ): Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_movie))
    }

    override fun onBindViewHolder(item: RemoteMovie, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: RemoteMovie){
            titleTextView.text = item.title
            yearTextView.text = item.year
            typeMovie.text = item.type
            imdb_id.text = item.id

            Glide.with(itemView)
                .load(item.poster)
                .placeholder(R.drawable.ic_image)
                .into(posterMovie)
        }
    }
}