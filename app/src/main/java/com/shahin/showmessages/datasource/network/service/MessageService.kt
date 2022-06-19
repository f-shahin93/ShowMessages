package com.shahin.showmessages.datasource.network.service

import com.shahin.showmessages.datasource.network.model.MessageBodyDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MessageService {

    @GET("{token}")
    suspend fun getMessagesList(@Path("token") token: String): MessageBodyDto

}