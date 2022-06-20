package com.shahin.showmessages.ui.pager

data class MessagesActions(
    val onReadStatusUpdateItem: (id: String) -> Unit,
    val onDeleteItem: (id: List<String>) -> Unit,
    val onShareClicked: (title: String, txtMessage: String) -> Unit,
    val onSaveClicked: (id: String) -> Unit
)