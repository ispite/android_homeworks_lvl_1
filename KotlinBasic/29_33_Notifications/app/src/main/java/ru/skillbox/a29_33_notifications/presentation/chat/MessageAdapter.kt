package ru.skillbox.a29_33_notifications.presentation.chat

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.skillbox.a29_33_notifications.data.Message

class MessageAdapter : AsyncListDifferDelegationAdapter<Message>(MessagesDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(MyMessageDelegate())
            .addDelegate(OpponentMessageDelegate())
    }

    class MessagesDiffUtilCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return when {
                oldItem is Message.MyMessage && newItem is Message.MyMessage -> oldItem.id == newItem.id
                oldItem is Message.OpponentMessage && newItem is Message.OpponentMessage -> oldItem.id == newItem.id
                else -> false
            }
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

    }
}