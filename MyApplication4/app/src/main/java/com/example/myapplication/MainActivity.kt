package com.example.myapplication


import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var stopAdapter: StopAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var tvProgress: TextView
    private lateinit var btnToggleUnit: Button
    private lateinit var btnNextStop: Button
    private var stopList = mutableListOf<Stop>()
    private var isMiles = false
    private var totalDistance = 0
    private var coveredDistance = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        recyclerView = findViewById(R.id.recyclerViewStops)
        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tvProgress)
        btnToggleUnit = findViewById(R.id.btnToggleUnit)
        btnNextStop = findViewById(R.id.btnNextStop)

        // Load stops from file
        loadStopsFromFile()

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        stopAdapter = StopAdapter(stopList, isMiles)
        recyclerView.adapter = stopAdapter

        // Calculate total distance
        totalDistance = stopList.sumOf { it.distanceKm }

        // Set initial progress
        updateProgress()

        // Toggle unit button
        btnToggleUnit.setOnClickListener {
            isMiles = !isMiles
            btnToggleUnit.text = if (isMiles) "Switch to KM" else "Switch to Miles"
            stopAdapter.toggleUnits()
            updateProgress()
        }

        // Next stop reached button
        btnNextStop.setOnClickListener {
            val distanceCovered = stopAdapter.markNextStopAsReached()
            coveredDistance += distanceCovered
            updateProgress()

            // Check if journey is complete
            if (coveredDistance >= totalDistance) {
                showCompletionMessage()
                resetJourney()
            }
        }
    }

    // Function to load stops from a file in assets folder
    private fun loadStopsFromFile() {
        try {
            val inputStream = assets.open("stops.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val stopsList = mutableListOf<Stop>()
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                val parts = line!!.split(",")
                if (parts.size == 3) {
                    val name = parts[0].trim()
                    val distance = parts[1].trim().toInt()
                    val visa = parts[2].trim()
                    stopsList.add(Stop(name, distance, visa))
                }
            }

            reader.close()

            // 🔹 Use Lazy Loading if stops > 3
            if (stopsList.size > 3) {
                recyclerView.setItemViewCacheSize(3)  // Load only 3 stops at a time
            }

            stopList.clear()
            stopList.addAll(stopsList)
            stopAdapter.notifyDataSetChanged()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    // Function to update progress
    private fun updateProgress() {
        val remainingDistance = totalDistance - coveredDistance
        tvProgress.text = "Distance Covered: $coveredDistance km | Distance Left: $remainingDistance km"

        val progressPercentage = if (totalDistance > 0) (coveredDistance * 100) / totalDistance else 0
        progressBar.progress = progressPercentage
    }

    // Function to show completion message
    private fun showCompletionMessage() {
        Toast.makeText(this, "Journey Completed! Resetting...", Toast.LENGTH_SHORT).show()
    }

    // Function to reset the journey
    private fun resetJourney() {
        coveredDistance = 0
        stopAdapter.resetStops() // Reset all stops in RecyclerView
        updateProgress()
    }
}
