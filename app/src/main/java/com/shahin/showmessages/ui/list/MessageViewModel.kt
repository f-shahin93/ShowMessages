package com.shahin.showmessages.ui.list


import androidx.lifecycle.ViewModel
import com.shahin.showmessages.datasource.repository.MessageRepository
import javax.inject.Inject

class MessageViewModel @Inject constructor(
    messageRepository: MessageRepository
) : ViewModel() {

}