package ru.skillbox.a221_261_jsonandretrofit.ui.current_user

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteUser

class UserAdapter : AsyncListDifferDelegationAdapter<RemoteUser>(
    UserDiffUtilCallback()
) {

    init {
        delegatesManager.addDelegate(UserAdapterDelegate())
    }

    class UserDiffUtilCallback : DiffUtil.ItemCallback<RemoteUser>() {
        override fun areItemsTheSame(oldItem: RemoteUser, newItem: RemoteUser): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RemoteUser, newItem: RemoteUser): Boolean {
            return oldItem == newItem
        }
    }
}