package com.shahin.showmessages.ui.pager

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahin.showmessages.datasource.model.Message
import com.shahin.showmessages.datasource.model.SingleEvent
import com.shahin.showmessages.datasource.repository.MessageRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessageViewModel @Inject constructor(
    private val messageRepository: MessageRepository
) : ViewModel() {

    val messagesSuccess = MutableLiveData<List<Message>>()
    val messagesLoading = MutableLiveData<Boolean>()
    val messagesError = MutableLiveData<Throwable>()

    val savedMessagesSuccess = MediatorLiveData<List<Message>>()
    val savedMessagesError = MediatorLiveData<Throwable>()

    val onNeededToShare = MutableLiveData<SingleEvent<Pair<String, String>>>()

    fun onSavedMessage(id: String) {
        messageRepository.savedMessage(id)
    }

    fun onReadMessageStatusUpdate(id: String) {
        messageRepository.readStatusUpdateMessage(id)
    }

    fun onDeleteMessage(id: List<String>) {
        messageRepository.deleteMessages(id)
    }

    init {
        viewModelScope.launch {
            messageRepository.getMessagesAll().collect {
                messagesSuccess.postValue(it)
            }
        }

        savedMessagesSuccess.addSource(messagesSuccess) {
            val list = it.filter { item -> item.saved }
            savedMessagesSuccess.postValue(list)
        }

        savedMessagesError.addSource(messagesError) {
            savedMessagesError.postValue(it)
        }

    }

}