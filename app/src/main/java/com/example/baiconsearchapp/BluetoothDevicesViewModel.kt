package com.example.baiconsearchapp

import android.bluetooth.le.ScanResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.altbeacon.beacon.Beacon


class BluetoothDevicesViewModel: ViewModel() {

    private val _isBeaconsLoading = MutableLiveData(true)
    val isBeaconsLoading: LiveData<Boolean> = _isBeaconsLoading

    private val _isBleDevicesLoading = MutableLiveData(true)
    val isBleDevicesLoading: LiveData<Boolean> = _isBleDevicesLoading

    private val _beaconList = MutableLiveData<List<Beacon>>()
    val beaconList: LiveData<List<Beacon>> = _beaconList

    private val _bleDeviceList = MutableLiveData<List<ScanResult>>()
    val bleDeviceList: LiveData<List<ScanResult>> = _bleDeviceList

    fun updateBeaconList(beacons: List<Beacon>){
        if (beacons.isNotEmpty()){
            _beaconList.value = beacons.sortedBy { it.distance }
        }
        _isBeaconsLoading.value = false
    }

    fun updateBleDeviceList(bleDevices: List<ScanResult>){
        if (bleDevices.isNotEmpty()){
            _bleDeviceList.value = bleDevices
        }
        _isBleDevicesLoading.value = false
    }
}