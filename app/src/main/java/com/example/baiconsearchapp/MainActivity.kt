package com.example.baiconsearchapp

import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseSettings
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.baiconsearchapp.BeaconsFragment.BeaconItemClickListener
import org.altbeacon.beacon.*


class MainActivity : AppCompatActivity(), BeaconConsumer, BeaconItemClickListener {

    private lateinit var beaconManager: BeaconManager
    private val viewModel: BeaconsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //transmitAsBeacon()
        beaconManager = BeaconManager.getInstanceForApplication(this)
        BeaconManager.setBeaconSimulator(MyBeaconSimulator())

        beaconManager.foregroundBetweenScanPeriod = 5000L
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(BeaconParser.ALTBEACON_LAYOUT))
        beaconManager.bind(this)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.fragments_container, BeaconsFragment.newInstance())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        beaconManager.unbind(this)
    }

    override fun moveToBeaconDetails(beaconId: String) {
        supportFragmentManager.commit {
            addToBackStack(null)
            add(R.id.fragments_container, BeaconDetailsFragment.newInstance(beaconId))
        }
    }

    override fun onBeaconServiceConnect() {

        beaconManager.removeAllMonitorNotifiers()
        beaconManager.addRangeNotifier { beacons, region ->
            if (beacons.isNotEmpty()) {
                viewModel.updateBeacons(beacons.toList())
                Log.d(TAG, "Found beacons -> count:  " + beacons.size);
                Log.d(TAG, "The first beacon I see is about " + beacons.iterator().next().distance + " meters away.")
            }
        }

        try {
            beaconManager.startRangingBeaconsInRegion(Region("myRangingUniqueId", null, null, null))
        }
        catch (e: Exception){
            Log.d(TAG, "Something is wrong " + e.message)
        }
    }

    private fun transmitAsBeacon() {
        val beacon = Beacon.Builder()
            .setId1("2f234454-cf6d-4a0f-adf2-f4911ba9ffa6")
            .setId2("1")
            .setId3("2")
            .setManufacturer(0x0118)
            .setTxPower(-59)
            .setDataFields(listOf(0L))
            .build()
        val beaconParser = BeaconParser().setBeaconLayout(BeaconParser.ALTBEACON_LAYOUT)
        val beaconTransmitter = BeaconTransmitter(applicationContext, beaconParser)

        beaconTransmitter.startAdvertising(beacon, object : AdvertiseCallback() {
            override fun onStartSuccess(settingsInEffect: AdvertiseSettings) {
                Log.i(TAG, "Advertisement start succeeded.");
            }

            override fun onStartFailure(errorCode: Int) {
                Log.e(TAG, "Advertisement start failed with code: $errorCode");
            }
        })
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}