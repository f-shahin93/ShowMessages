package com.shahin.showmessages.app

import android.app.Application
import com.shahin.showmessages.di.ApplicationGraph
import com.shahin.showmessages.di.DaggerApplicationGraph

class MainApplication : Application() {
    lateinit var applicationGraph: ApplicationGraph
        private set

    override fun onCreate() {
        super.onCreate()
        applicationGraph = DaggerApplicationGraph.builder()
            .application(this)
            .build()
    }

}