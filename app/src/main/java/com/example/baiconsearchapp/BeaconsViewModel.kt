package com.example.baiconsearchapp

import android.bluetooth.le.ScanResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.altbeacon.beacon.Beacon


class BeaconsViewModel: ViewModel() {

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _beaconList = MutableLiveData<List<Beacon>>()
    val beaconList: LiveData<List<Beacon>> = _beaconList

    private val _bleDeviceList = MutableLiveData<List<ScanResult>>()
    val bleDeviceList: LiveData<List<ScanResult>> = _bleDeviceList

    fun updateBeaconList(beacons: List<Beacon>){
        if (beacons.isNotEmpty()){
            _beaconList.value = beacons.sortedBy { it.distance }
        }
        _isLoading.value = false
    }

    fun updateBleDeviceList(bleDevices: List<ScanResult>){
        if (bleDevices.isNotEmpty()){
            _bleDeviceList.value = bleDevices
        }
    }
}