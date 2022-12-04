package ru.skillbox.a29_33_notifications.data

sealed class Message {
    data class MyMessage(
        val id: Long,
        val text: String
    ) : Message()

    data class OpponentMessage(
        val id: Long,
        val text: String,
        val avatar: String
    ) : Message()
}
