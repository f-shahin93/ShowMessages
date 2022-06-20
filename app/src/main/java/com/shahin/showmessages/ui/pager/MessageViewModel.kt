package com.shahin.showmessages.ui.pager


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahin.showmessages.datasource.model.DataResult
import com.shahin.showmessages.datasource.model.Message
import com.shahin.showmessages.datasource.repository.MessageRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessageViewModel @Inject constructor(
    messageRepository: MessageRepository
) : ViewModel() {

    val messagesSuccess = MutableLiveData<List<Message>>()
    val messagesLoading = MutableLiveData<Boolean>()
    val messagesError = MutableLiveData<Throwable>()

    init {
        viewModelScope.launch {
            messageRepository.getMessagesAll().collect { result ->
                when (result) {
                    is DataResult.Success -> {
                        messagesSuccess.postValue(result.data ?: emptyList())
                    }
                    is DataResult.Loading -> {
                        messagesLoading.postValue(result.data == null)
                    }
                    is DataResult.Error -> {
                        messagesError.postValue(result.message ?: Throwable())
                    }
                }
            }
        }
    }

}