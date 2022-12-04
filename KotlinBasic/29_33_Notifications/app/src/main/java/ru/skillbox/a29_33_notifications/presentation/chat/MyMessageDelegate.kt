package ru.skillbox.a29_33_notifications.presentation.chat

import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.skillbox.a29_33_notifications.data.Message
import ru.skillbox.a29_33_notifications.databinding.ItemMyMessageBinding
import ru.skillbox.a29_33_notifications.utils.inflate

class MyMessageDelegate :
    AbsListItemAdapterDelegate<Message.MyMessage, Message, MyMessageDelegate.MyMessageHolder>() {

    override fun isForViewType(item: Message, items: MutableList<Message>, position: Int): Boolean {
        return item is Message.MyMessage
    }

    override fun onCreateViewHolder(parent: ViewGroup): MyMessageHolder {
        return MyMessageHolder(parent.inflate(ItemMyMessageBinding::inflate))
    }

    override fun onBindViewHolder(
        item: Message.MyMessage,
        holder: MyMessageHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class MyMessageHolder(binding: ItemMyMessageBinding) : MessageViewHolder(binding.root) {

        fun bind(item: Message.MyMessage) {
            bindGeneralInfo(item.text)
        }
    }
}
