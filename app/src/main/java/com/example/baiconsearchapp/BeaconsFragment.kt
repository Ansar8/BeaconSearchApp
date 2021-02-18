package com.example.baiconsearchapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baiconsearchapp.BeaconsAdapter.*
import org.altbeacon.beacon.Beacon

class BeaconsFragment : Fragment(R.layout.fragment_beacon_list) {

    private var listener: BeaconItemClickListener? = null
    private val viewModel: BluetoothDevicesViewModel by activityViewModels()
    private lateinit var recycler: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as? MainActivity
        activity?.supportActionBar?.title = "Beacons list"

        recycler = view.findViewById(R.id.recycler_view_beacons)
        recycler.adapter = BeaconsAdapter(clickListener)
        recycler.layoutManager = LinearLayoutManager(context)

        progressBar = view.findViewById(R.id.beacons_progress_bar)

        viewModel.beaconList.observe(this.viewLifecycleOwner, this::updateBeaconList)
        viewModel.isBeaconsLoading.observe(this.viewLifecycleOwner, this::showProgressBar)
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

    interface BeaconItemClickListener {
        fun moveToBeaconDetails(beaconId: String)
    }

    companion object {
        fun newInstance() = BeaconsFragment()
    }
}