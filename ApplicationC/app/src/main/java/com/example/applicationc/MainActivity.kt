package com.example.applicationc

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.applicationc.Stop

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val stops = remember { loadStopsFromFile(applicationContext, "stops.txt") }
            AppNavigation(navController, stops)
        }
    }
}

private fun loadStopsFromFile(context: Context, filename: String): List<Stop> {
    val stops = mutableListOf<Stop>()
    try {
        context.assets.open(filename).bufferedReader().use { reader ->
            reader.forEachLine { line ->
                val data = line.split(",").map { it.trim() }
                if (data.size == 3) {
                    stops.add(
                        Stop(
                            name = data[0],
                            distanceKm = data[1].toInt(),
                            visaRequired = data[2].toBooleanStrict()
                        )
                    )
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return stops
}