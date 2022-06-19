package com.shahin.showmessages.datasource.repository


import com.shahin.showmessages.datasource.model.DataResult
import com.shahin.showmessages.datasource.model.Message
import com.shahin.showmessages.datasource.network.service.MessageService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface MessageRepository {
    fun getMessagesAll(): Flow<DataResult<List<Message>>>
    fun getSavedMessages(): Flow<DataResult<List<Message>>>
}

class DefaultMessageRepository @Inject constructor(
    private val messageService:MessageService,
) : MessageRepository {

    override fun getMessagesAll(): Flow<DataResult<List<Message>>> {
        TODO("Not yet implemented")
    }

    override fun getSavedMessages(): Flow<DataResult<List<Message>>> {
        TODO("Not yet implemented")
    }

}