package com.shahin.showmessages.ui.pager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import com.shahin.showmessages.datasource.model.SingleEvent
import com.shahin.showmessages.di.ViewModelFactory
import com.shahin.showmessages.ui.MainActivity
import com.shahin.showmessages.ui.base.BaseComposeFragment
import com.shahin.showmessages.ui.pager.composeviews.MessagesListView
import com.shahin.showmessages.ui.utils.MessageTheme
import javax.inject.Inject

class PublicMessageListFragment : BaseComposeFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MessageViewModel by lazy {
        ViewModelProvider(
            requireParentFragment(),
            viewModelFactory
        )[MessageViewModel::class.java]
    }

    private lateinit var actions: MessagesActions

    override fun onAttach(context: Context) {
        (requireActivity() as MainActivity).mainActivitySubComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        composeRootView.setContent {
            MessageTheme {
                Surface {
                    ListScreen()
                }
            }
        }
        return composeRootView.rootView
    }

    @Composable
    private fun ListScreen() {

        actions = MessagesActions(
            onShareClicked = { title, txtMessage ->
                viewModel.onNeededToShare.postValue(SingleEvent(title to txtMessage))
            },
            onSaveClicked = { id -> viewModel.onSavedMessage(id) },
            onReadStatusUpdateItem = { id -> viewModel.onReadMessageStatusUpdate(id) },
            onDeleteItem = { ids -> viewModel.onDeleteMessage(ids) },
        )

        val list = viewModel.messagesSuccess.observeAsState()
        MessagesListView(list.value ?: emptyList(), actions)
    }

}