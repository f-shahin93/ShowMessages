package com.shahin.showmessages.di.datasource

import com.shahin.showmessages.datasource.repository.MessageRepository
import com.shahin.showmessages.datasource.repository.DefaultMessageRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindMessageRepository(repository: DefaultMessageRepository): MessageRepository

}