package com.shahin.showmessages.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.compose.ui.platform.ComposeView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment


abstract class BaseComposeFragment : Fragment() {

    private var _composeRootView: ComposeView? = null
    protected val composeRootView get() = _composeRootView!!

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = ComposeView(inflater.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            _composeRootView = this
        }
        _composeRootView = view
        return view.rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _composeRootView = null
    }

}


abstract class BaseFragment<B : ViewDataBinding>(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    private var _binding: B? = null
    protected val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        if (view != null) {
            _binding = DataBindingUtil.bind<B>(view)!!.apply {
                lifecycleOwner = viewLifecycleOwner
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}