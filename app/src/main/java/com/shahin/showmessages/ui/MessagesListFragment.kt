package com.shahin.showmessages.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import com.shahin.showmessages.di.ViewModelFactory
import com.shahin.showmessages.ui.base.BaseComposeFragment
import com.shahin.showmessages.ui.pager.MessageViewModel
import com.shahin.showmessages.ui.pager.PublicMessages
import com.shahin.showmessages.ui.utils.MessageTheme
import javax.inject.Inject

class MessagesListFragment : BaseComposeFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: MessageViewModel by lazy {
        ViewModelProvider(
            requireParentFragment(),
            viewModelFactory
        )[MessageViewModel::class.java]
    }

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
        val list = viewModel.messagesSuccess.observeAsState()
        PublicMessages(list.value ?: emptyList(), {}, {})
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}