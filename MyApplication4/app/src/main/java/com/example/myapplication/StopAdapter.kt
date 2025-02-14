package com.example.myapplication

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StopAdapter(private val stopList: MutableList<Stop>, private var isMiles: Boolean) :
    RecyclerView.Adapter<StopAdapter.StopViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.stop_item, parent, false)
        return StopViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StopViewHolder, position: Int) {
        val stop = stopList[position]
        holder.bind(stop, isMiles)
    }

    override fun getItemCount(): Int = stopList.size

    fun toggleUnits() {
        isMiles = !isMiles
        notifyDataSetChanged()
    }

    fun markNextStopAsReached(): Int {
        for (stop in stopList) {
            if (!stop.isReached) {
                stop.isReached = true
                notifyDataSetChanged()  // 🔹 Force RecyclerView to update UI
                return stop.distanceKm
            }
        }
        return 0
    }

    fun resetStops() {
        for (stop in stopList) {
            stop.isReached = false
        }
        notifyDataSetChanged()  // 🔹 Ensure UI is reset
    }

    class StopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvStopName: TextView = itemView.findViewById(R.id.tvStopName)
        private val tvDistance: TextView = itemView.findViewById(R.id.tvDistance)
        val tvVisaRequirement: TextView = itemView.findViewById(R.id.tvVisaRequirement)

        fun bind(stop: Stop, isMiles: Boolean) {
            tvStopName.text = stop.name
            val distance = if (isMiles) stop.distanceKm * 0.621 else stop.distanceKm.toDouble()
            tvDistance.text = "Distance: ${String.format("%.1f", distance)} ${if (isMiles) "miles" else "km"}"
            tvVisaRequirement.text = "Visa: ${stop.visaRequirement}"

            // 🔹 Change UI based on stop status
            if (stop.isReached) {
                tvStopName.setTextColor(Color.GRAY)  // Faded color
                tvVisaRequirement.setTextColor(Color.GREEN)   // Green "Reached" status
            } else {
                tvStopName.setTextColor(Color.BLACK) // Normal color
                tvVisaRequirement.setTextColor(Color.RED)     // Red "Not Reached" status
            }
        }
    }
}
