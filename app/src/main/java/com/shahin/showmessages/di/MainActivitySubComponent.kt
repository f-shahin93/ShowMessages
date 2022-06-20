package com.shahin.showmessages.di


import com.shahin.showmessages.ui.MessagesListFragment
import com.shahin.showmessages.ui.pager.MessagePagerFragment
import dagger.Subcomponent

@Subcomponent
interface MainActivitySubComponent {

    fun inject(messagePagerFragment: MessagePagerFragment)
    fun inject(messagesListFragment: MessagesListFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainActivitySubComponent
    }

}