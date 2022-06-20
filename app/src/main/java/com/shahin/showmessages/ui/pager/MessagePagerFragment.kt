package com.shahin.showmessages.ui.pager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.shahin.showmessages.R
import com.shahin.showmessages.databinding.FragmentMessagePagerBinding
import com.shahin.showmessages.datasource.model.SingleEventObserver
import com.shahin.showmessages.ui.MainActivity
import com.shahin.showmessages.di.ViewModelFactory
import com.shahin.showmessages.ui.base.BaseFragment
import javax.inject.Inject


class MessagePagerFragment : BaseFragment<FragmentMessagePagerBinding>(
    R.layout.fragment_message_pager
) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by viewModels<MessageViewModel> { viewModelFactory }

    private lateinit var pagerAdapter: PageAdapter

    override fun onAttach(context: Context) {
        (requireActivity() as MainActivity).mainActivitySubComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupTabsPager()

    }

    private fun setupListeners() {
        viewModel.onNeededToShare.observe(viewLifecycleOwner, SingleEventObserver {
            onShareClicked(title = it.first, txtMessage = it.second)
        })
    }

    private fun setupTabsPager() {
        initViewPager()
        initTabLayout()
    }

    private fun initViewPager() {
        pagerAdapter = PageAdapter(
            this,
            listOf(PublicMessageListFragment(), SavedMessageListFragment())
        )
        binding.pager.adapter = pagerAdapter
    }

    private fun initTabLayout() {
        TabLayoutMediator(binding.tabs, binding.pager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.label_public)
                else -> getString(R.string.label_saved)
            }
        }.attach()
    }

    private fun onShareClicked(title: String, txtMessage: String) {
        startActivity(
            Intent.createChooser(
                Intent(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_SUBJECT, title)
                    .putExtra(Intent.EXTRA_TEXT, txtMessage)
                    .apply {
                        type = "text/plain"
                        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    }, title
            )
        )
    }


}