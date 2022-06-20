package com.shahin.showmessages.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shahin.showmessages.datasource.local.model.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(messages: List<MessageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(messageEntity: MessageEntity)

    @Query("SELECT * FROM my_message ORDER BY  unread = 1 DESC , unread = 0 DESC, id DESC")
    fun getAllMessages(): Flow<List<MessageEntity>>

    @Query("SELECT COUNT(*) FROM my_message")
    fun getCountMessages(): Int

    @Query("UPDATE my_message SET unread = 0 WHERE id = :id")
    fun updateReadStatusMessage(id: String): Int

    @Query("UPDATE my_message SET saved = NOT saved WHERE id = :id")
    fun updateSavedStatusMessage(id: String): Int

    @Query("DELETE FROM my_message WHERE id IN (:ids)")
    suspend fun deleteMessage(ids: List<String>)

}