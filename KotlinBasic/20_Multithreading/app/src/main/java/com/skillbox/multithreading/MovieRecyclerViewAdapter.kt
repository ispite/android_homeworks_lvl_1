package com.skillbox.multithreading

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skillbox.multithreading.networking.Movie
import com.skillbox.multithreading.threading.ThreadingViewModel
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieRecyclerViewAdapter(
) : RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieHolder>() {

    private var movieList: List<Movie> = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val currentItem = movieList[position]
        holder.textView.text = currentItem.title
    }

    override fun getItemCount() = movieList.size

    class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.movieTitleTextView
    }

    fun setMovieList(movieList: List<Movie>) {
        this.movieList = movieList.toMutableList()
        notifyDataSetChanged()
    }
}