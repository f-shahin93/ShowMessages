package com.shahin.showmessages.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.shahin.showmessages.R
import com.shahin.showmessages.app.MainApplication
import com.shahin.showmessages.databinding.ActivityMainBinding
import com.shahin.showmessages.di.MainActivitySubComponent

class MainActivity : AppCompatActivity() {

    lateinit var mainActivitySubComponent: MainActivitySubComponent

    override fun onCreate(savedInstanceState: Bundle?) {

        mainActivitySubComponent =
            (applicationContext as MainApplication).applicationGraph.mainComponent().create()

        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

    }
}