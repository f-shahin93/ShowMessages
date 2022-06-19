package com.shahin.showmessages.datasource.network.model

import com.google.gson.annotations.SerializedName

data class MessageBodyDto(
    @SerializedName("messages")
    val messages: List<MessageDto>
)

data class MessageDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("unread")
    val unread: Boolean,
)
