package com.shahin.showmessages.datasource.repository


import com.shahin.showmessages.datasource.local.dao.MessageDao
import com.shahin.showmessages.datasource.model.DataResult
import com.shahin.showmessages.datasource.model.Message
import com.shahin.showmessages.datasource.network.service.MessageService
import com.shahin.showmessages.datasource.network.token
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


interface MessageRepository {
    suspend fun getMessagesAll(): Flow<List<Message>>
    fun deleteMessages(id: List<String>)
    fun savedMessage(id: String)
    fun readStatusUpdateMessage(id: String)
}

class DefaultMessageRepository @Inject constructor(
    private val messageService: MessageService,
    private val messageDao: MessageDao
) : MessageRepository {

    override suspend fun getMessagesAll(): Flow<List<Message>> {
        getMessagesRemote()
        return messageDao.getAllMessages().map { it.map { it.toDomain() } }
    }

    override fun deleteMessages(id: List<String>) {
        CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { _, _ -> }).launch {
            messageDao.deleteMessage(id)
        }
    }

    override fun savedMessage(id: String) {
        CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { _, _ -> }).launch {
            messageDao.updateSavedStatusMessage(id = id)
        }
    }

    override fun readStatusUpdateMessage(id: String) {
        CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { _, _ -> }).launch {
            messageDao.updateReadStatusMessage(id)
        }
    }

    private fun getMessagesRemote() {
        CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { _, _ -> }).launch {
            val messageBody = messageService.getMessagesList(token)
            messageDao.insert(messageBody.messages.map { it.toEntity() })
        }
    }

}