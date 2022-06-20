package com.shahin.showmessages.ui.pager

/**
 * Create this class for handle actions of compose views.
 */

data class MessagesActions(
    val onReadStatusUpdateItem: (id: String) -> Unit,
    val onDeleteItem: (id: List<String>) -> Unit,
    val onShareClicked: (title: String, txtMessage: String) -> Unit,
    val onSaveClicked: (id: String) -> Unit
)