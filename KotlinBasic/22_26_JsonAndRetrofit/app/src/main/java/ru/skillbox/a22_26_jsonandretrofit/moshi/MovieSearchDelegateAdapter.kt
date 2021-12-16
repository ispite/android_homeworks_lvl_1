package ru.skillbox.a22_26_jsonandretrofit.moshi

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.*
import ru.skillbox.a22_26_jsonandretrofit.R
import ru.skillbox.a22_26_jsonandretrofit.utils.inflate

class MovieSearchDelegateAdapter :
    AbsListItemAdapterDelegate<Movie, Movie, MovieSearchDelegateAdapter.Holder>() {

    override fun isForViewType(
        item: Movie,
        items: MutableList<Movie>,
        position: Int
    ): Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_movie))
    }

    override fun onBindViewHolder(item: Movie, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: Movie) {
            titleTextView.text = item.title
            yearTextView.text = item.year.toString()
            ageRatingMovie.text = item.ageRating.toString()
            genreMovie.text = item.genre
            linkToPosterMovie.text = item.poster.toString()
            //scoresMovie.text = item.scores.toString()
            scoresMovie.text = item.scores.map { it.toString() }.joinToString("\n")
            //typeMovie.text = item.type
            //imdb_id.text = item.id

            Glide.with(itemView)
                .load(item.poster.toString())
                .placeholder(R.drawable.ic_image)
                .into(posterMovie)
        }
    }
}