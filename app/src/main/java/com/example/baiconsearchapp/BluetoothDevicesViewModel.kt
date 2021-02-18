package com.example.baiconsearchapp

import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.os.Build
import android.util.Log
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
            _isBeaconsLoading.value = false
        }
    }

    fun updateBleDeviceList(bleDevices: List<ScanResult>){
        if (bleDevices.isNotEmpty()){
            _bleDeviceList.value = bleDevices
            _isBleDevicesLoading.value = false
        }
    }

    private val bleScanner = BluetoothAdapter.getDefaultAdapter().bluetoothLeScanner

    fun startScanning(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val scanSettings = ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
                .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
                .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
                .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
                .setReportDelay(5000L)
                .build()

            if (bleScanner != null) {
                bleScanner.startScan(null, scanSettings, scanCallback)
                Log.d(TAG, "scan started")
            } else {
                Log.e(TAG, "could not get scanner object")
            }
        }
    }

    private fun stopScanning(){
        bleScanner.stopScan(scanCallback)
    }

    private val scanCallback = object : ScanCallback() {
        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            if (results?.isNotEmpty() == true) {
                updateBleDeviceList(results)
            }
            Log.i(TAG, "onBatchScanResults: amount of found ble devices is ${results?.size ?: 0}")
        }
        override fun onScanFailed(errorCode: Int) {
            Log.e(TAG, "onScanFailed: code $errorCode")
        }
    }

    override fun onCleared() {
        stopScanning()
        super.onCleared()
    }

    companion object {
        private const val TAG = "ViewModel"
    }
}