package ru.skillbox.a29_33_notifications.presentation.chat

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.skillbox.a29_33_notifications.R

abstract class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val messageTextView: TextView = itemView.findViewById(R.id.message)

    protected fun bindGeneralInfo(message: String) {
        messageTextView.text = message
    }
}