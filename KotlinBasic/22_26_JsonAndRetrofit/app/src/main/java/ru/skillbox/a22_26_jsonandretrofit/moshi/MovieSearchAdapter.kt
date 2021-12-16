package ru.skillbox.a22_26_jsonandretrofit.moshi

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class MovieSearchAdapter :
    AsyncListDifferDelegationAdapter<Movie>(MovieSearchDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(MovieSearchDelegateAdapter())
    }

    fun setEmptyList() {
        differ.submitList(null)
    }

    class MovieSearchDiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            //return oldItem.id == newItem.id
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}