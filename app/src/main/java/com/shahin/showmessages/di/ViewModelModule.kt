package com.shahin.showmessages.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shahin.showmessages.ui.list.MessageViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MessageViewModel::class)
    abstract fun bindMessageViewModel(messageViewModel: MessageViewModel): ViewModel

}