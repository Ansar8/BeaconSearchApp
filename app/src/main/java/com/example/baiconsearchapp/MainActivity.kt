package com.example.baiconsearchapp

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.baiconsearchapp.fragments.BeaconsFragment.BeaconItemClickListener
import com.example.baiconsearchapp.fragments.BeaconDetailsFragment
import com.example.baiconsearchapp.fragments.DevicesViewPagerFragment
import org.altbeacon.beacon.*

class MainActivity : AppCompatActivity(), BeaconItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            verifyBluetooth()
            askAndCheckPermissions()

            supportFragmentManager.commit {
                add(R.id.fragments_container, DevicesViewPagerFragment())
            }
        }
    }

    override fun moveToBeaconDetails(beaconId: String) {
        supportFragmentManager.commit {
            addToBackStack(null)
            add(R.id.fragments_container, BeaconDetailsFragment.newInstance(beaconId))
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

    companion object {
        private const val TAG = "MainActivity"
        private const val PERMISSION_REQUEST_FINE_LOCATION = 1
    }
}