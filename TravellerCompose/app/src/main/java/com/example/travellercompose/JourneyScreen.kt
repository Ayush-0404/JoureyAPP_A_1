package com.example.travellercompose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun JourneyScreen(
    navController: NavController,
    start: String,
    destination: String,
    allStops: List<Stop>
) {
    var currentStopIndex by remember { mutableStateOf(0) }
    var showMiles by remember { mutableStateOf(false) }

    val journeyStops = remember(allStops) {
        allStops.filter { it.name != start }.toMutableList().apply {
            add(Stop(destination, 0, false))
        }
    }

    val totalDistance = journeyStops.sumOf { it.distanceKm }
    val distanceCovered = journeyStops.take(currentStopIndex).sumOf { it.distanceKm }
    val distanceLeft = totalDistance - distanceCovered

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Header
        Text(
            text = "$start to $destination",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Progress Section
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Covered: ${formatDistance(distanceCovered, showMiles)}")
                    Text("Left: ${formatDistance(distanceLeft, showMiles)}")
                }

                LinearProgressIndicator(
                    progress = if (totalDistance > 0) distanceCovered.toFloat() / totalDistance else 0f,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )

                Button(
                    onClick = { showMiles = !showMiles },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(if (showMiles) "Switch to km" else "Switch to miles")
                }
            }
        }

        // Stops List
        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(journeyStops) { index, stop ->
                StopItem(
                    stop = stop,
                    isReached = index < currentStopIndex,
                    isCurrent = index == currentStopIndex,
                    showMiles = showMiles
                )
            }
        }

        // Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { currentStopIndex = 0 }) {
                Text("Reset")
            }

            Button(
                onClick = { if (currentStopIndex < journeyStops.size - 1) currentStopIndex++ },
                enabled = currentStopIndex < journeyStops.size - 1
            ) {
                Text("Next Stop")
            }
        }
    }
}
//@Composable
//fun JourneyScreen(
//    navController: NavController,
//    start: String,
//    destination: String,
//    selectedRoute: String,
//    allStops: List<Stop>
//) {
//    var currentStopIndex by remember { mutableStateOf(0) }
//    var showMiles by remember { mutableStateOf(false) }
//
//    // Filter stops based on selected route
//    val routeStops = remember(selectedRoute, allStops) {
//        allStops.filter { stop ->
//            when (selectedRoute) {
//                "Route A - Via Frankfurt" -> stop.name.contains("Frankfurt", ignoreCase = true)
//                "Route B - Via Dubai" -> stop.name.contains("Dubai", ignoreCase = true)
//                "Route C - Via Singapore" -> stop.name.contains("Singapore", ignoreCase = true)
//                else -> false
//            }
//        }.toMutableList().apply {
//            add(Stop(destination, 0, visaRequired = false))
//        }
//    }
//
//    val totalDistance = routeStops.sumOf { it.distanceKm }
//    val distanceCovered = routeStops.take(currentStopIndex).sumOf { it.distanceKm }
//    val distanceLeft = totalDistance - distanceCovered
//
//    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//        // Header Section
//        Column(modifier = Modifier.padding(bottom = 16.dp)) {
//            Text(
//                text = "$start → $destination",
//                style = MaterialTheme.typography.headlineSmall
//            )
//            Text(
//                text = "Route: $selectedRoute",
//                style = MaterialTheme.typography.bodyMedium,
//                color = MaterialTheme.colorScheme.onSurfaceVariant
//            )
//        }
//
//        // Progress Section
//        Card(
//            modifier = Modifier.padding(bottom = 16.dp),
//            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
//        ) {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Text(
//                    text = "Journey Progress",
//                    style = MaterialTheme.typography.titleMedium,
//                    modifier = Modifier.padding(bottom = 8.dp)
//                )
//
//                if (totalDistance > 0) {
//                    LinearProgressIndicator(
//                        progress = distanceCovered.toFloat() / totalDistance,
//                        modifier = Modifier.fillMaxWidth().height(8.dp)
//                    )
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Row(horizontalArrangement = Arrangement.SpaceBetween,
//                        modifier = Modifier.fillMaxWidth()) {
//                        Text("Covered: ${formatDistance(distanceCovered, showMiles)}")
//                        Text("Remaining: ${formatDistance(distanceLeft, showMiles)}")
//                    }
//                } else {
//                    Text("No route information available",
//                        color = MaterialTheme.colorScheme.error)
//                }
//
//                Button(
//                    onClick = { showMiles = !showMiles },
//                    modifier = Modifier.align(Alignment.End)
//                ) {
//                    Text(if (showMiles) "Show km" else "Show miles")
//                }
//            }
//        }
//
//        // Stops List
//        if (routeStops.isNotEmpty()) {
//            LazyColumn(modifier = Modifier.weight(1f)) {
//                itemsIndexed(routeStops) { index, stop ->
//                    StopItem(
//                        stop = stop,
//                        isReached = index < currentStopIndex,
//                        isCurrent = index == currentStopIndex,
//                        showMiles = showMiles
//                    )
//                }
//            }
//        } else {
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text("No stops found for this route",
//                    color = MaterialTheme.colorScheme.error)
//            }
//        }
//
//        // Control Buttons
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Button(
//                onClick = { currentStopIndex = 0 },
//                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
//            ) {
//                Text("Reset Journey")
//            }
//
//            Button(
//                onClick = { if (currentStopIndex < routeStops.size - 1) currentStopIndex++ },
//                enabled = currentStopIndex < routeStops.size - 1
//            ) {
//                Text("Next Stop Reached")
//            }
//        }
//    }
//}

@Composable
private fun StopItem(
    stop: Stop,
    isReached: Boolean,
    isCurrent: Boolean,
    showMiles: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isCurrent -> MaterialTheme.colorScheme.primaryContainer
                isReached -> MaterialTheme.colorScheme.surfaceVariant
                else -> MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = if (isReached) MaterialTheme.colorScheme.primary else Color.Transparent,
                modifier = Modifier.size(24.dp).padding(end = 8.dp)
            )

            Column {
                Text(
                    text = stop.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isCurrent) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Distance: ${formatDistance(stop.distanceKm, showMiles)}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Visa required: ${if (stop.visaRequired) "Yes" else "No"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun formatDistance(km: Int, showMiles: Boolean): String {
    return if (showMiles) "${(km * 0.621371).toInt()} mi" else "$km km"
}