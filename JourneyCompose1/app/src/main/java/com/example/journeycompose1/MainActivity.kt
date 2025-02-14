package com.example.journeycompose1

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import java.io.BufferedReader
import java.io.InputStreamReader

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
        context.assets.open(filename).use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                reader.forEachLine { line ->
                    val cleanLine = line.trim()
                    if (cleanLine.isNotEmpty()) {
                        val data = cleanLine.split(",").map { it.trim() }
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
            }
        }
        // Debug log hai niche
        println("Loaded ${stops.size} stops: ${stops.joinToString { it.name }}")
    } catch (e: Exception) {
        println("Error loading stops: ${e.message}")
        e.printStackTrace()
    }
    return stops
}


// OTHER ONE

//package com.example.improvedapp
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Surface
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
////import com.example.improvedapp.ui.theme.AppTheme
//import com.example.improvedapp.ui.theme.ImprovedappTheme
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        // Retrieve the selected stops passed from the route selection screen.
//        val selectedStops = intent.getParcelableArrayListExtra<Stop>("SELECTED_STOPS")
//        if (selectedStops.isNullOrEmpty()) {
//            finish()
//            return
//        }
//        setContent {
//            ImprovedappTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    // MainScreen displays the journey progress and stops.
//                    MainScreen(selectedStops)
//                }
//            }
//        }
//    }
//}
//@Composable
//fun MainScreen(stops: List<Stop>) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Application(stops)
//    }
//}

