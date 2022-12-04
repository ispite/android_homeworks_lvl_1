package ru.skillbox.a29_33_notifications.presentation.chat

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.skillbox.a29_33_notifications.R
import ru.skillbox.a29_33_notifications.data.Message
import ru.skillbox.a29_33_notifications.databinding.ItemOpponentMessageBinding
import ru.skillbox.a29_33_notifications.utils.inflate

class OpponentMessageDelegate :
    AbsListItemAdapterDelegate<Message.OpponentMessage, Message, OpponentMessageDelegate.OpponentMessageHolder>() {

    override fun isForViewType(item: Message, items: MutableList<Message>, position: Int): Boolean {
        return item is Message.OpponentMessage
    }

    override fun onCreateViewHolder(parent: ViewGroup): OpponentMessageHolder {
        return OpponentMessageHolder(parent.inflate(ItemOpponentMessageBinding::inflate))
    }

    override fun onBindViewHolder(
        item: Message.OpponentMessage,
        holder: OpponentMessageHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class OpponentMessageHolder(private val binding: ItemOpponentMessageBinding) :
        MessageViewHolder(binding.root) {
        fun bind(item: Message.OpponentMessage) {
            bindGeneralInfo(item.text)

            Glide.with(itemView)
                .load(item.avatar)
                .placeholder(R.drawable.ic_image)
                .into(binding.opponentAvatar)
        }
    }
}