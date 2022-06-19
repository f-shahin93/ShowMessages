package com.shahin.showmessages.di


import dagger.Subcomponent

@Subcomponent
interface MainActivitySubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainActivitySubComponent
    }

}