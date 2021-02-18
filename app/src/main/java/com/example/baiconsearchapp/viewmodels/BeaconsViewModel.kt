package com.example.baiconsearchapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.altbeacon.beacon.Beacon

class BeaconsViewModel: ViewModel() {

    private val _isBeaconsLoading = MutableLiveData(true)
    val isBeaconsLoading: LiveData<Boolean> = _isBeaconsLoading

    private val _beaconList = MutableLiveData<List<Beacon>>()
    val beaconList: LiveData<List<Beacon>> = _beaconList

    fun updateBeaconList(beacons: List<Beacon>){
        if (beacons.isNotEmpty()){
            _beaconList.value = beacons.sortedBy { it.distance }
            _isBeaconsLoading.value = false
        }
    }

    companion object {
        private const val TAG = "BeaconsViewModel"
    }
}