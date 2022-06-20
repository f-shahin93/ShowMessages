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
    fun getMessagesAll(): Flow<DataResult<List<Message>>>
}

class DefaultMessageRepository @Inject constructor(
    private val messageService: MessageService,
    private val messageDao: MessageDao
) : MessageRepository {

    override fun getMessagesAll(): Flow<DataResult<List<Message>>> = flow {
        getMessagesRemote()
        emit(DataResult.Loading())
        try {
            withTimeout(3000) {
                messageDao.getMessages().collect {
                    emit(DataResult.Loading(it.map { it.toDomain() }))
                    emit(DataResult.Success(it.map { it.toDomain() }))
                }
            }
        } catch (ex: Exception) {
            emit(DataResult.Error(ex))
        }

    }.catch { ex -> emit(DataResult.Error(ex)) }
        .flowOn(Dispatchers.IO)


    private fun getMessagesRemote() {
        CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler { _, _ -> }).launch {
            val messageBody = messageService.getMessagesList(token)
            messageDao.insert(messageBody.messages.map { it.toEntity() })
        }
    }

}