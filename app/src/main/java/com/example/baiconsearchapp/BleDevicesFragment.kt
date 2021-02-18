package com.example.baiconsearchapp

import android.Manifest
import android.bluetooth.le.ScanResult
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ProgressBar
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BleDevicesFragment : Fragment(R.layout.fragment_ble_devices) {

    private val viewModel: BleDevicesViewModel by activityViewModels()
    private lateinit var recycler: RecyclerView
    private lateinit var progressBar: ProgressBar
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.recycler_view_ble_devices)
        recycler.adapter = BleDevicesAdapter()
        recycler.layoutManager = LinearLayoutManager(context)

        progressBar = view.findViewById(R.id.ble_devices_progress_bar)
        
        viewModel.bleDeviceList.observe(this.viewLifecycleOwner, this::updateBleDeviceList)
        viewModel.isBleDevicesLoading.observe(this.viewLifecycleOwner, this::showProgressBar)

        if (savedInstanceState == null){
            if (hasPermission())
                viewModel.startScanning()
        }
    }
    
    private fun updateBleDeviceList(scanResults: List<ScanResult>) {
        (recycler.adapter as? BleDevicesAdapter)?.apply {
            bindDevices(scanResults)
        }
    }

    private fun showProgressBar(isVisible: Boolean){
        progressBar.isVisible = isVisible
    }

    private fun hasPermission(): Boolean {
        val result = checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        fun newInstance() = BleDevicesFragment()
    }
}