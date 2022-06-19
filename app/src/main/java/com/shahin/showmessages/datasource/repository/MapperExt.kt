package com.shahin.showmessages.datasource.repository

import com.shahin.showmessages.datasource.local.model.MessageEntity
import com.shahin.showmessages.datasource.model.Message
import com.shahin.showmessages.datasource.network.model.MessageDto


fun MessageDto.toEntity(): MessageEntity =
    MessageEntity(
        id = id,
        title = title,
        description = description,
        image = image,
        unread = unread,
        saved = false
    )

fun MessageEntity.toDomain(): Message =
    Message(
        id = id,
        title = title,
        description = description,
        image = image,
        unread = unread,
        saved = saved
    )