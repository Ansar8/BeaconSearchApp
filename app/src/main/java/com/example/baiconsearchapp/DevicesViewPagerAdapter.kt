package com.example.baiconsearchapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DevicesViewPagerAdapter(fm: FragmentManager, lc: Lifecycle): FragmentStateAdapter(fm,lc) {

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> BleDevicesFragment.newInstance()
            1 -> BeaconsFragment.newInstance()
            else -> BleDevicesFragment.newInstance()
        }

    override fun getItemCount(): Int = 2
}