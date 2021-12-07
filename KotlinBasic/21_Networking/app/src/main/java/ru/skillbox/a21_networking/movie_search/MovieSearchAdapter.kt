package ru.skillbox.a21_networking.movie_search

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class MovieSearchAdapter: AsyncListDifferDelegationAdapter<RemoteMovie>(MovieSearchDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(MovieSearchDelegateAdapter())
    }

    class MovieSearchDiffUtilCallback: DiffUtil.ItemCallback<RemoteMovie>() {
        override fun areItemsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RemoteMovie, newItem: RemoteMovie): Boolean {
            return oldItem == newItem
        }
    }
}