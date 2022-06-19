package com.shahin.showmessages.datasource.local.model

import androidx.room.*


@Entity(tableName = "message")
data class MessageEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "unread")
    val unread: Boolean,
    @ColumnInfo(name = "saved")
    val saved: Boolean,
)