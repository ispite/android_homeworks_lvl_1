package ru.skillbox.a29_33_notifications.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.skillbox.a29_33_notifications.data.db.models.MessageDb
import ru.skillbox.a29_33_notifications.data.db.models.MessageContract

@Dao
interface MessageDao {

    @Query("SELECT * FROM ${MessageContract.TABLE_NAME}")
    fun getAllMessages(): Flow<List<MessageDb>>

    @Insert
    suspend fun insertMessage(messageDb: List<MessageDb>)
}