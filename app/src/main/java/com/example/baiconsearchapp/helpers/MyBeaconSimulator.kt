package com.example.baiconsearchapp.helpers

import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.simulator.BeaconSimulator
import java.util.*

class MyBeaconSimulator: BeaconSimulator {

    override fun getBeacons(): MutableList<Beacon> {
        return mutableListOf(
            Beacon.Builder()
                .setId1(UUID.randomUUID().toString())
                .setId2("1")
                .setId3("1")
                .setRssi((-55..-45).random())
                .setTxPower(-45)
                .build(),
            Beacon.Builder()
                .setId1(UUID.randomUUID().toString())
                .setId2("3")
                .setId3("4")
                .setRssi((-55..-45).random())
                .setTxPower(-59)
                .build(),
            Beacon.Builder()
                .setId1(UUID.randomUUID().toString())
                .setId2("7")
                .setId3("8")
                .setRssi((-55..-45).random())
                .setTxPower(-55)
                .build()
        )
    }
}