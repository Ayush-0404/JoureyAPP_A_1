package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class StopAdapter (private val stops : List<Stop>,private var isMiles: Boolean) : RecyclerView.Adapter<StopAdapter.StopViewHolder>(){

    class StopViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val stopName: TextView = itemView.findViewById(R.id.tvStopName)
        val distance: TextView = itemView.findViewById(R.id.tvDistance)
        val visaRequired: TextView = itemView.findViewById(R.id.tvVisa)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stop_item, parent, false)

        return StopViewHolder(view)
    }

    override fun onBindViewHolder(holder: StopViewHolder, position: Int) {
        val stop = stops[position]
        holder.stopName.text = stop.name
        holder.distance.text = "Distance: ${stop.getDist(isMiles)} "
        holder.visaRequired.text = "Visa Required: ${if (stop.visaRequired) "Yes" else "No"}"
        holder.visaRequired.setTextColor(
            if (stop.visaRequired) android.graphics.Color.RED else android.graphics.Color.GREEN
        )
        if (stop.reached){
            holder.itemView.alpha= 0.5f
        }else {
            holder.itemView.alpha = 1.0f
        }
    }

    override fun getItemCount() = stops.size
    fun toggleUnits() {
        isMiles = !isMiles
        notifyDataSetChanged()
    }

    fun markNextStopReached(): Int{
        for(stop in stops){
            if (!stop.reached){
                stop.reached = true
                notifyDataSetChanged()
                //
                return stop.distanceFromLast
            }
        }
        return 0
    }
}
