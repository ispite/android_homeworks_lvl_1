package ru.skillbox.a221_261_jsonandretrofit.ui.current_user

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user.*
import ru.skillbox.a221_261_jsonandretrofit.R
import ru.skillbox.a221_261_jsonandretrofit.data.RemoteUser
import ru.skillbox.a221_261_jsonandretrofit.utils.inflate

class UserAdapterDelegate :
    AbsListItemAdapterDelegate<RemoteUser, RemoteUser, UserAdapterDelegate.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup): Holder {
        return Holder(parent.inflate(R.layout.item_user))
    }

    override fun isForViewType(
        item: RemoteUser,
        items: MutableList<RemoteUser>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onBindViewHolder(item: RemoteUser, holder: Holder, payloads: MutableList<Any>) {
        holder.bind(item)
    }

    class Holder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(item: RemoteUser) {
            usernameTextView.text = item.username
            Glide.with(itemView)
                .load(item.avatar)
                .transform(CircleCrop())
                .placeholder(R.drawable.ic_emoji)
                .into(avatarImageView)
        }
    }
}