package com.example.baiconsearchapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import org.altbeacon.beacon.Beacon

class BeaconDetailsFragment : Fragment(R.layout.fragment_beacon_details) {

    private var beaconId: String? = null
    private val viewModel: BeaconsViewModel by activityViewModels()

    private lateinit var uuidTextView: TextView
    private lateinit var majorTextView: TextView
    private lateinit var minorTextView: TextView
    private lateinit var rssiTextView: TextView
    private lateinit var distanceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            beaconId = it.getString(PARAM_BEACON_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uuidTextView = view.findViewById(R.id.uuid_details_tv)
        majorTextView = view.findViewById(R.id.major_details_tv)
        minorTextView = view.findViewById(R.id.minor_details_tv)
        rssiTextView = view.findViewById(R.id.rssi_details_tv)
        distanceTextView = view.findViewById(R.id.distance_details_tv)

        viewModel.beaconList.observe(this.viewLifecycleOwner,this::showBeaconDetails)
    }

    private fun showBeaconDetails(beacons: List<Beacon>) {
        val beacon: Beacon? = beacons.find { it.id1.toString() == beaconId }
        if (beacon != null){
            uuidTextView.text = beacon.id1.toString()
            majorTextView.text = beacon.id2.toString()
            minorTextView.text = beacon.id3.toString()
            rssiTextView.text = beacon.rssi.toString()
            distanceTextView.text = requireContext().getString(
                R.string.distance_in_meters,
                String.format("%.3f", beacon.distance)
            )
        }
        else{
            uuidTextView.text = beaconId
            majorTextView.text = "-"
            minorTextView.text = "-"
            rssiTextView.text = "-"
            distanceTextView.text = "-"
        }
    }

    companion object {
        private const val PARAM_BEACON_ID = "beaconId"

        fun newInstance(beaconId: String) =
            BeaconDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(PARAM_BEACON_ID, beaconId)
                }
            }
    }
}