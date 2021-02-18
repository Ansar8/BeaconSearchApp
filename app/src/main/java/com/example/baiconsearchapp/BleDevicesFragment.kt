package com.example.baiconsearchapp

import android.bluetooth.le.ScanResult
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BleDevicesFragment : Fragment(R.layout.fragment_ble_devices) {

    private val viewModel: BluetoothDevicesViewModel by activityViewModels()
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
    }
    
    private fun updateBleDeviceList(scanResults: List<ScanResult>) {
        (recycler.adapter as? BleDevicesAdapter)?.apply {
            bindDevices(scanResults)
        }
    }

    private fun showProgressBar(isVisible: Boolean){
        progressBar.isVisible = isVisible
    }

    companion object {
        fun newInstance() = BleDevicesFragment()
    }
}