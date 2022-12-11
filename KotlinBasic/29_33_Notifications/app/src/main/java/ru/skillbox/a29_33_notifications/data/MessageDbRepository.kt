package ru.skillbox.a29_33_notifications.data

import kotlinx.coroutines.flow.Flow
import ru.skillbox.a29_33_notifications.data.db.Database
import ru.skillbox.a29_33_notifications.data.db.models.MessageDb

class MessageDbRepository {
    private val messageDao = Database.instance.messageDao()

    fun getAllMessages(): Flow<List<MessageDb>> {
        return messageDao.getAllMessages()
    }

    suspend fun insertMessage(messageDb: MessageDb) {
        if (isMessageValid(messageDb).not()) throw RuntimeException("incorrect form")
        messageDao.insertMessage(listOf(messageDb))
    }

    private fun isMessageValid(messageDb: MessageDb): Boolean {
        // Простая проверка на валидность в качестве примера
        return messageDb.content.isNotEmpty()
    }
}