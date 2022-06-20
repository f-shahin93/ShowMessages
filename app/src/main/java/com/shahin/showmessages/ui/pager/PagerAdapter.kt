package com.shahin.showmessages.ui.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class PageAdapter(fragment: Fragment, val fragments: List<Fragment>) :
    FragmentStateAdapter(fragment) {

    /*var pages: List<Fragment> = pages.toList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }*/

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

}