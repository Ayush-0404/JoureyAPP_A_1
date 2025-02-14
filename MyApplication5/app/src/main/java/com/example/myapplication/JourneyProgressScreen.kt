package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JourneyProgressScreen(navController: NavController) {
    val sampleStops = listOf(
        Stop("New Delhi", true, 0.0),
        Stop("Dubai", true, 2500.0),
        Stop("London", true, 8000.0)
    )

    var currentStopIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Journey Tracker") },
                navigationIcon = {
                    IconButton({ navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CircularProgressIndicator(
                progress = currentStopIndex.toFloat() / (sampleStops.size - 1),
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                strokeWidth = 8.dp,
                strokeCap = StrokeCap.Round
            )

            Spacer(Modifier.height(24.dp))

            LazyColumn {
                itemsIndexed(sampleStops) { index, stop ->
                    StopItem(stop, index == currentStopIndex)
                }
            }
        }
    }
}

@Composable
fun StopItem(stop: Stop, isCurrent: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(stop.name, style = MaterialTheme.typography.titleMedium)
            Text("Visa required: ${if (stop.visaRequired) "Yes" else "No"}")
            Text("Distance: ${stop.distanceFromPrevious} Km")
        }
    }
}

data class Stop(
    val name: String,
    val visaRequired: Boolean,
    val distanceFromPrevious: Double
)