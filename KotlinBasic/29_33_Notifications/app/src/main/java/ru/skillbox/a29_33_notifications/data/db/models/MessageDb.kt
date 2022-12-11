package ru.skillbox.a29_33_notifications.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.skillbox.a29_33_notifications.data.Message

@Entity(tableName = MessageContract.TABLE_NAME)
data class MessageDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = MessageContract.Columns.ID)
    val id: Long,

    @ColumnInfo(name = MessageContract.Columns.HOST)
    val host: String,

    @ColumnInfo(name = MessageContract.Columns.CONTENT)
    val content: String
) {
    companion object {
        fun convertFromDb(messageDb: MessageDb): Message {
            return if (messageDb.host == ME) {
                Message.MyMessage(messageDb.id, messageDb.content)
            } else {
                Message.OpponentMessage(messageDb.id, messageDb.content, messageDb.host)
            }
        }

        private const val ME = "ME"
    }
}