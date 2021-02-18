package com.example.baiconsearchapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.baiconsearchapp.R
import com.example.baiconsearchapp.adapters.DevicesViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DevicesViewPagerFragment: Fragment(R.layout.fragment_devices_view_pager) {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.viewpager)
        tabLayout = view.findViewById(R.id.tabLayout)

        setupAdapter()
        setupTabLayout()
    }

    private fun setupAdapter() {
        val pagerAdapter = DevicesViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = pagerAdapter
        viewPager.setPageTransformer(MarginPageTransformer(500));
    }

    private fun setupTabLayout(){
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "BLE"
                1 -> tab.text = "BEACONS"
            }
        }.attach()
    }



}