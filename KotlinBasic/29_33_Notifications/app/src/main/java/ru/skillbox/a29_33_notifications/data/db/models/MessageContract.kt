package ru.skillbox.a29_33_notifications.data.db.models

object MessageContract {
    const val TABLE_NAME = "messages"

    object Columns {
        const val ID = "id"
        const val HOST = "host"
        const val CONTENT = "content"
    }
}