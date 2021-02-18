package com.example.baiconsearchapp

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baiconsearchapp.BeaconsAdapter.*
import org.altbeacon.beacon.*

class BeaconsFragment : Fragment(R.layout.fragment_beacon_list), BeaconConsumer {

    private var listener: BeaconItemClickListener? = null
    private val viewModel: BeaconsViewModel by activityViewModels()
    private lateinit var beaconManager: BeaconManager
    private lateinit var recycler: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        beaconManager = BeaconManager.getInstanceForApplication(requireActivity())
        //Provides simulated beacons as a working example
        BeaconManager.setBeaconSimulator(MyBeaconSimulator())
        beaconManager.foregroundBetweenScanPeriod = 3000L
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(BeaconParser.ALTBEACON_LAYOUT))
        beaconManager.bind(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.recycler_view_beacons)
        recycler.adapter = BeaconsAdapter(clickListener)
        recycler.layoutManager = LinearLayoutManager(context)

        progressBar = view.findViewById(R.id.beacons_progress_bar)

        viewModel.beaconList.observe(this.viewLifecycleOwner, this::updateBeaconList)
        viewModel.isBeaconsLoading.observe(this.viewLifecycleOwner, this::showProgressBar)
    }

    override fun onBeaconServiceConnect() {
        beaconManager.removeAllMonitorNotifiers()
        beaconManager.addRangeNotifier { beacons, region ->
            if (beacons.isNotEmpty()) {
                viewModel.updateBeaconList(beacons.toList())
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

    override fun getApplicationContext(): Context = requireActivity().applicationContext


    override fun unbindService(serviceConnection: ServiceConnection) {
        requireActivity().unbindService(serviceConnection)
    }

    override fun bindService(intent: Intent, serviceConnection: ServiceConnection, i: Int): Boolean {
        return requireActivity().bindService(intent, serviceConnection, i)
    }

    private fun updateBeaconList(beacons: List<Beacon>) {
        (recycler.adapter as? BeaconsAdapter)?.apply {
            bindBeacons(beacons)
        }
    }

    private fun showProgressBar(isVisible: Boolean){
        progressBar.isVisible = isVisible
    }

    private val clickListener = object : OnRecyclerItemClicked {
        override fun onClick(beaconId: String) {
            listener?.moveToBeaconDetails(beaconId)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BeaconItemClickListener) listener = context
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    override fun onDestroy() {
        beaconManager.unbind(this)
        super.onDestroy()
    }

    interface BeaconItemClickListener {
        fun moveToBeaconDetails(beaconId: String)
    }

    companion object {
        private const val TAG = "BeaconsFragment"
        fun newInstance() = BeaconsFragment()
    }
}