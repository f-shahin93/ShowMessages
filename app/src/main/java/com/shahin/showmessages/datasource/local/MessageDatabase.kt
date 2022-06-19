package com.shahin.showmessages.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shahin.showmessages.datasource.local.dao.MessageDao
import com.shahin.showmessages.datasource.local.model.MessageEntity


@Database(entities = [MessageEntity::class], version = 1)
abstract class MessageDatabase : RoomDatabase() {
    abstract val messageDao: MessageDao
}