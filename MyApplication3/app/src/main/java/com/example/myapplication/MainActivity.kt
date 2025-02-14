package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    // Declare UI Elements
    private lateinit var tvJourneyDetails: TextView
    private lateinit var btnNextStop_reached: Button
//    private lateinit var btnToggleDistance: Button
    private lateinit var btn_switch_button: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerViewStops: RecyclerView
    private lateinit var stopAdapter: StopAdapter
// for lazy l
    private var stopList: MutableList<Stop> = mutableListOf()
    private var isMiles = false

//hardcoded one
//    private val stopList = listOf(
//        Stop("New York", 0, false),
//        Stop("London", 5570, true),
//        Stop("Dubai", 5500, true),
//        Stop("Delhi", 2200, false),
//        Stop("Tokyo", 5900, true)
//    )


//    private var isKm = true // Track distance unit (KM/Miles)
    private var distanceCovered = 0
    private var totalDistance = 0 // Example total distance in KM
    private var stopsReached = 0
    private var totalStops = 5 // Example total stops


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        tvJourneyDetails = findViewById(R.id.tvJourneyDetails)
        btn_switch_button = findViewById(R.id.btn_switch_button)
        btnNextStop_reached = findViewById(R.id.btnNextStop_reached)
        progressBar = findViewById(R.id.progressBar)
        recyclerViewStops = findViewById(R.id.recyclerViewStops)

        //3rd
        stopList = loadStopsFromFile("stops.txt")
        //2nd
        recyclerViewStops.layoutManager = LinearLayoutManager(this)
        stopAdapter = StopAdapter(stopList,isMiles)
        recyclerViewStops.adapter = stopAdapter

        // Update initial journey details
        updateJourneyDetails()

        // Toggle Distance Button Click Listener
        btn_switch_button.setOnClickListener {
            isMiles = !isMiles
            stopAdapter.toggleUnits()
            btn_switch_button.text = if (isMiles) "Switch to KM" else "Switch to Miles"
            updateJourneyDetails()
        }

        // Next Stop Button Click Listener
        btnNextStop_reached.setOnClickListener {
            val nextStopDistance = stopAdapter.markNextStopReached()
            distanceCovered += nextStopDistance
            updateJourneyDetails()
            // 1st one ->
//            if (stopsReached < totalStops) {
//                stopsReached++
//                distanceCovered += totalDistance / totalStops
//                updateJourneyDetails()
//            }
        }
    }


    // Function to Update Journey Details
    private fun updateJourneyDetails() {
        val distanceLeft = totalDistance - distanceCovered
        val unit = if (isMiles) "mi" else "km"
        tvJourneyDetails.text = "Distance Covered: ${convertDistance(distanceCovered)} $unit | Distance Left: ${convertDistance(distanceLeft)} $unit"

        val progressPercent = if (totalDistance > 0) (distanceCovered.toFloat() / totalDistance * 100).toInt() else 0
        progressBar.progress = progressPercent
//        val displayedDistance = if (isKm) distanceCovered else (distanceCovered * 0.621371).toInt()
//        val displayedTotalDistance = if (isKm) totalDistance else (totalDistance * 0.621371).toInt()
//        val displayedRemainingDistance = displayedTotalDistance - displayedDistance
//
//        tvJourneyDetails.text = "Distance Covered: $displayedDistance ${if (isKm) "KM" else "Miles"} | Distance Left: $displayedRemainingDistance ${if (isKm) "KM" else "Miles"}"
//
//        progressBar.progress = (distanceCovered * 100) / totalDistance
    }

    private fun convertDistance(distance: Int): String {
        return if (isMiles) String.format("%.1f", distance * 0.621371) else distance.toString()
    }

    private fun loadStopsFromFile(filename: String): MutableList<Stop> {
        val stops = mutableListOf<Stop>()
        try {
            val inputStream = assets.open(filename)
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val data = line!!.split(",")
                if (data.size == 3) {
                    val name = data[0].trim()
                    val distance = data[1].trim().toInt()
                    val visaRequired = data[2].trim().toBoolean()
                    stops.add(Stop(name, distance, visaRequired))
                }
            }
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return stops
    }


}

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//}