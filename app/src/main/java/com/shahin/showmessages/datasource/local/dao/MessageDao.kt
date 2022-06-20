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

    @Query("SELECT * FROM my_message")
    fun getMessages(): Flow<List<MessageEntity>>

    @Query("SELECT COUNT(*) FROM my_message")
    fun getCountMessages(): Int

}