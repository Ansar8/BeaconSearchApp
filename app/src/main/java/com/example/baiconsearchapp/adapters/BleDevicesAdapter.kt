package com.example.baiconsearchapp.adapters

import android.bluetooth.le.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baiconsearchapp.R

class BleDevicesAdapter : RecyclerView.Adapter<BleDeviceViewHolder>() {

    private var scanResults = listOf<ScanResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BleDeviceViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.view_holder_ble_device, parent, false)
        return BleDeviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: BleDeviceViewHolder, position: Int) {
        holder.onBind(scanResults[position])
    }

    override fun getItemCount(): Int = scanResults.size

    fun bindDevices(scanResults: List<ScanResult>) {
        this.scanResults = scanResults
        notifyDataSetChanged()
    }
}

class BleDeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun onBind(scanResult: ScanResult) {

        val mac = scanResult.device.address
        val rssi = scanResult.rssi

        itemView.findViewById<TextView>(R.id.ble_device_mac_tv).apply {
            text =  this@BleDeviceViewHolder.context.getString(R.string.mac_text, mac.toString())
        }
        itemView.findViewById<TextView>(R.id.ble_device_rssi_tv).apply {
            text = this@BleDeviceViewHolder.context.getString(R.string.rssi_text, rssi.toString())
        }

    }
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context