package com.example.travellercompose

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

// Move the loadStopsFromFile function here
private fun loadStopsFromFile(context: Context, filename: String): List<Stop> {
    val stops = mutableListOf<Stop>()
    try {
        context.assets.open(filename).use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                reader.forEachLine { line ->
                    val data = line.split(",")
                    if (data.size == 3) {
                        stops.add(
                            Stop(
                                name = data[0].trim(),
                                distanceKm = data[1].trim().toInt(),
                                visaRequired = data[2].trim().toBooleanStrict()
                            )
                        )
                    }
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return stops
}
//import android.content.Context
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.travellercompose.ui.theme.TravellerComposeTheme
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.navigation.NavHost
//import androidx.navigation.compose.rememberNavController
//import java.io.BufferedReader
//import java.io.InputStreamReader
//
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            val navController = rememberNavController()
//            AppNavigation(navController)
//                }
//            }
//        }
//
//fun loadStopsFromFile(context: Context, filename: String): List<Stop> {
//    val stops = mutableListOf<Stop>()
//    try {
//        val inputStream = context.assets.open(filename)
//        val reader = BufferedReader(InputStreamReader(inputStream))
//        reader.useLines { lines ->
//            lines.forEach { line ->
//                val data = line.split(",")
//                if (data.size == 3) {
//                    val name = data[0].trim()
//                    val distance = data[1].trim().toInt()
//                    val visaRequired = data[2].trim().toBoolean()
//                    stops.add(Stop(name, distance, visaRequired))
//                }
//            }
//        }
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//    return stops
//}

//@Composable
//fun JourneyApp() {
//    val navController = rememberNavController()
//
//    NavHost(navController = navController, startDestination = "start_screen") {
//        composable("start_screen") { StartScreen(navController) }
//        composable("path_selection_screen") { PathSelectionScreen(navController) }
//        composable("journey_progress_screen") { JourneyProgressScreen(navController) }
//    }
//}


