package com.example.baiconsearchapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.altbeacon.beacon.Beacon


class BeaconsViewModel: ViewModel() {

    private val _beaconList = MutableLiveData<List<Beacon>>(emptyList())
    val beaconList: LiveData<List<Beacon>> = _beaconList

    fun updateBeacons(beacons: List<Beacon>){
        if (beacons.isNotEmpty()){
            _beaconList.value = beacons.sortedBy { it.distance }
        }
    }
}