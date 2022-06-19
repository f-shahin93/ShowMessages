package com.shahin.showmessages.datasource.model


data class Message(
    val id: String,
    val title: String,
    val description: String,
    val image: String,
    val unread: Boolean,
    val saved: Boolean,
)