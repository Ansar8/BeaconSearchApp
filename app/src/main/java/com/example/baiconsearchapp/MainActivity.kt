package com.example.baiconsearchapp

import android.Manifest
import android.app.AlertDialog
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseSettings
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.baiconsearchapp.BeaconsFragment.BeaconItemClickListener
import org.altbeacon.beacon.*

class MainActivity : AppCompatActivity(), BeaconConsumer, BeaconItemClickListener {

    private lateinit var beaconManager: BeaconManager
    private val viewModel: BeaconsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verifyBluetooth()
        askAndCheckPermissions()
        setupBeaconManager()
        //transmitAsBeacon()

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
                Log.d(TAG, "Found beacons -> count:  " + beacons.size)
            }
        }

        try {
            beaconManager.startRangingBeaconsInRegion(Region("myRangingUniqueId", null, null, null))
        }
        catch (e: Exception){
            Log.d(TAG, "Something is wrong " + e.message)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_FINE_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "fine location permission granted")
                } else {
                    showAlert(
                        title = getString(R.string.func_limited_alert_title),
                        message = getString(R.string.location_access_not_granted_alert_message)
                    )
                }
                return
            }
        }
    }

    private fun setupBeaconManager(){
        beaconManager = BeaconManager.getInstanceForApplication(this)
        BeaconManager.setBeaconSimulator(MyBeaconSimulator())

        beaconManager.foregroundBetweenScanPeriod = 5000L
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(BeaconParser.ALTBEACON_LAYOUT))
        beaconManager.bind(this)
    }

    private fun askAndCheckPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    requestPermissions(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        PERMISSION_REQUEST_FINE_LOCATION
                    )
                } else {
                    showAlert(
                        title = getString(R.string.func_limited_alert_title),
                        message = getString(R.string.location_access_not_granted_full_alert_message)
                    )
                }
            }
        }
    }

    private fun verifyBluetooth() {
        try {
            if (!BeaconManager.getInstanceForApplication(this).checkAvailability()) {
                showAlert(
                    title = getString(R.string.bluetooth_not_enabled_alert_title),
                    message = getString(R.string.bluetooth_not_enabled_alert_message)
                )
            }
        } catch (e: RuntimeException) {
            showAlert(
                title = getString(R.string.bluetooth_le_not_available_alert_title),
                message = getString(R.string.bluetooth_le_not_available_alert_message)
            )
        }
    }

    private fun showAlert(title: String, message: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(android.R.string.ok, null)
        builder.setOnDismissListener { }
        builder.show()
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
        private const val PERMISSION_REQUEST_FINE_LOCATION = 1
    }
}