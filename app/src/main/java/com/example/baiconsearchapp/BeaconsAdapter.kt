package com.example.baiconsearchapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.altbeacon.beacon.Beacon

class BeaconsAdapter(
    private val clickListener: OnRecyclerItemClicked
): RecyclerView.Adapter<BeaconViewHolder>() {

    interface OnRecyclerItemClicked {
        fun onClick(beaconId: String)
    }

    private var beacons = listOf<Beacon>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeaconViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.view_holder_beacon, parent, false)
        return BeaconViewHolder(view)
    }

    override fun onBindViewHolder(holder: BeaconViewHolder, position: Int) {
        holder.onBind(beacons[position])
        holder.itemView.setOnClickListener {
            val beaconId = beacons[position].id1.toString()
            clickListener.onClick(beaconId)
        }
    }

    override fun getItemCount(): Int = beacons.size

    fun bindBeacons(newBeacons: List<Beacon>) {
        beacons = newBeacons
        notifyDataSetChanged()
    }
}

class BeaconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun onBind(beacon: Beacon) {

        itemView.findViewById<TextView>(R.id.uuid_tv).apply {
            text =  this@BeaconViewHolder.context.getString(R.string.uuid_text, beacon.id1.toString())
        }
        itemView.findViewById<TextView>(R.id.major_tv).apply {
            text = this@BeaconViewHolder.context.getString(R.string.major_text, beacon.id2.toString())
        }
        itemView.findViewById<TextView>(R.id.minor_tv).apply {
            text = this@BeaconViewHolder.context.getString(R.string.minor_text, beacon.id3.toString())
        }
        itemView.findViewById<TextView>(R.id.rssi_tv).apply {
            text = this@BeaconViewHolder.context.getString(R.string.rssi_text, beacon.rssi.toString())
        }
        itemView.findViewById<TextView>(R.id.distance_tv).apply {
            text = this@BeaconViewHolder.context.getString(
                R.string.distance_text,
                String.format("%.3f", beacon.distance))
        }

    }
}

private val RecyclerView.ViewHolder.context
    get() = this.itemView.context