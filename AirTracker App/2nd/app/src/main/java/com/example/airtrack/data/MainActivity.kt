package com.example.airtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.airtrack.ui.theme.AirTrackTheme


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.airtrack.data.RouteAverage
import com.example.airtrack.ui.theme.ErrorBoundary
import com.example.airtrack.viewmodels.FlightViewModel
import java.util.concurrent.TimeUnit


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AirTrackTheme {
                Surface {
                    ErrorBoundary {
                        AverageFlightTimesScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun AverageFlightTimesScreen(viewModel: FlightViewModel = viewModel()) {
    val averages by viewModel.averages.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Average Flight Durations (Last 7 Days)",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (averages.isEmpty()) {
            Text("No data available. Background job may still be running.")
        } else {
            LazyColumn {
                items(averages) { avg ->
                    RouteAverageCard(avg)
                }
            }
        }
    }
}


@Composable
fun RouteAverageCard(avg: RouteAverage) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${avg.origin} → ${avg.destination}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Average Duration: ${formatDuration(avg.avgDuration)}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

private fun formatDuration(millis: Double): String {
    val hours = TimeUnit.MILLISECONDS.toHours(millis.toLong())
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis.toLong()) % 60
    return "${hours}h ${minutes}m"
}
