package com.shahin.showmessages.di


import com.shahin.showmessages.ui.pager.PublicMessageListFragment
import com.shahin.showmessages.ui.pager.MessagePagerFragment
import com.shahin.showmessages.ui.pager.SavedMessageListFragment
import dagger.Subcomponent

@Subcomponent
interface MainActivitySubComponent {

    fun inject(messagePagerFragment: MessagePagerFragment)
    fun inject(publicMessageListFragment: PublicMessageListFragment)
    fun inject(savedMessageListFragment: SavedMessageListFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainActivitySubComponent
    }

}