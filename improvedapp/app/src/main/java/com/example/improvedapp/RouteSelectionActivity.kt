package com.example.improvedapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import java.io.BufferedReader
import java.io.InputStreamReader

class RouteSelectionActivity : ComponentActivity() {
    private lateinit var availableRoutes: Map<Int, List<Stop>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load routes from file
        availableRoutes = loadRoutesFromFile()

        setContent {
            RouteSelectionScreen(availableRoutes) { selectedStops ->
                val intent = Intent(this, MainActivity::class.java)
                intent.putParcelableArrayListExtra("SELECTED_STOPS", ArrayList(selectedStops))
                startActivity(intent)
                finish()
            }
        }
    }

    private fun loadRoutesFromFile(): Map<Int, List<Stop>> {
        val routes = mutableMapOf<Int, List<Stop>>()
        val inputStream = assets.open("Stops.txt")
        val reader = BufferedReader(InputStreamReader(inputStream))

        reader.useLines { lines ->
            lines.forEach { line ->
                val parts = line.split("|").map { it.trim() }

                if (parts.size > 2) {
                    val routeIndex = parts[0].toIntOrNull() ?: return@forEach
                    val stopsList = parts.drop(1).mapNotNull { stopData ->
                        val stopDetails = stopData.split(";")
                        if (stopDetails.size == 3) {
                            Stop(
                                name = stopDetails[0].trim(),
                                visaRequired = stopDetails[1].trim().toBoolean(),
                                distanceFromPrevious = stopDetails[2].trim().toDoubleOrNull() ?: 0.0
                            )
                        } else {
                            null
                        }
                    }
                    if (stopsList.isNotEmpty()) {
                        routes[routeIndex] = stopsList
                    }
                }
            }
        }
        return routes
    }
}
