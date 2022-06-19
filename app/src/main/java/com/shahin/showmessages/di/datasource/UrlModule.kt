package com.shahin.showmessages.di.datasource

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class UrlModule {

    companion object {
        const val WEBSITE_ENDPOINT = "https://run.mocky.io/"
        const val BASE_PATH = "v3/"
    }

    @Provides
    @Named("baseUrl")
    fun provideBaseUrl(): String = WEBSITE_ENDPOINT + BASE_PATH

}