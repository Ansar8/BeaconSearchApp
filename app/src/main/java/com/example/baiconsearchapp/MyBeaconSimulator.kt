package com.example.baiconsearchapp

import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.simulator.BeaconSimulator

class MyBeaconSimulator: BeaconSimulator {

    override fun getBeacons(): MutableList<Beacon> {
        return mutableListOf(
            Beacon.Builder()
                .setId1("DF7E1C79-43E9-44FF-886F-1D1F7DA6997A")
                .setId2("1")
                .setId3("1")
                .setRssi(-55)
                .setTxPower(-45)
                .build()
        )
    }
}