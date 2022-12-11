package ru.skillbox.a29_33_notifications.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.skillbox.a29_33_notifications.data.MessageDbRepository
import ru.skillbox.a29_33_notifications.data.db.models.MessageDb

class ChatViewModel : ViewModel() {

    private val repository = MessageDbRepository()

    val messageListFlow = repository.getAllMessages().mapLatest { messageList ->
        messageList.map { MessageDb.convertFromDb(it) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addMyMessageToDb(messageDb: MessageDb) {
        viewModelScope.launch {
            repository.insertMessage(messageDb)
        }
    }
}