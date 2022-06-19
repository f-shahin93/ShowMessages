package com.shahin.showmessages.di.datasource

import android.content.Context
import androidx.room.Room
import com.shahin.showmessages.datasource.local.MessageDatabase
import com.shahin.showmessages.datasource.local.dao.MessageDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideMessageDatabase(context: Context):MessageDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            MessageDatabase::class.java,
            "message_db"
        ).build()


    @Provides
    @Singleton
    fun provideMessageDao(messageDatabase: MessageDatabase): MessageDao =
        messageDatabase.messageDao

}