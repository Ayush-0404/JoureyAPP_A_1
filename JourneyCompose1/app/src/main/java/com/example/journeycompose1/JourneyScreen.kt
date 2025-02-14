package com.example.journeycompose1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll


@Composable
fun JourneyScreen(
    navController: NavController,
    start: String,
    destination: String,
    allStops: List<Stop>
) {
    // taking the Clean inputs
    val cleanStart = start.trim()
    val cleanDest = destination.trim()

    // works even with case-insensitive match
    val startStop = allStops.firstOrNull { it.name.equals(cleanStart, true) }
    val destStop = allStops.firstOrNull { it.name.equals(cleanDest, true) }

    // Validation done below
    if (startStop == null || destStop == null) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Invalid cities!", color = MaterialTheme.colorScheme.error)
            Spacer(Modifier.height(8.dp))
            Text("Available cities:", style = MaterialTheme.typography.bodyMedium)
            Text(
                allStops.joinToString("\n") { "- ${it.name}" },
                modifier = Modifier.padding(16.dp)
            )
        }
        return
    }

    val startIndex = allStops.indexOf(startStop)
    val destIndex = allStops.indexOf(destStop)

    if (startIndex >= destIndex) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Invalid route!", color = MaterialTheme.colorScheme.error)
            Text("Destination must come after start city")
            Text("Correct order:")
            Text(allStops.joinToString { it.name })
        }
        return
    }

    val routeStops = allStops.subList(startIndex, destIndex + 1)
    val totalDistance = routeStops.last().distanceKm - routeStops.first().distanceKm

    var currentStopIndex by remember { mutableStateOf(0) }
    var showMiles by remember { mutableStateOf(false) }

    val currentPosition = routeStops[currentStopIndex].distanceKm - routeStops.first().distanceKm
    val distanceCovered = currentPosition
    val distanceLeft = totalDistance - distanceCovered

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        // putting Header
        Text(
            text = "${routeStops.first().name} to ${routeStops.last().name}",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Progress section display
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(Modifier.padding(16.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
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

        // for visual indication , stop list etc.
        // Conditional Stops List:
        if (routeStops.size > 3) {
            // Used  LazyColumn only  if there are more than 3 as asked by sir stops
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                itemsIndexed(routeStops) { index, stop ->
                    val segmentDistance = if (index == 0) 0 else stop.distanceKm - routeStops[index - 1].distanceKm

                    StopItem(
                        stop = stop,
                        segmentDistance = segmentDistance,
                        isReached = index < currentStopIndex,
                        isCurrent = index == currentStopIndex,
                        showMiles = showMiles
                    )
                }
            }
        } else {
            // Used a simple Column with vertical scrolling if 3 or less stops are there a
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                routeStops.forEachIndexed { index, stop ->
                    val segmentDistance = if (index == 0) 0 else stop.distanceKm - routeStops[index - 1].distanceKm

                    StopItem(
                        stop = stop,
                        segmentDistance = segmentDistance,
                        isReached = index < currentStopIndex,
                        isCurrent = index == currentStopIndex,
                        showMiles = showMiles
                    )
                }
            }
        }

        // Controls are below
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = { currentStopIndex = 0 }) {
                Text("Reset")
            }

            Button(
                onClick = { if (currentStopIndex < routeStops.size - 1) currentStopIndex++ },
                enabled = currentStopIndex < routeStops.size - 1
            ) {
                Text("Next Stop")
            }
        }
    }
}

@Composable
private fun StopItem(
    stop: Stop,
    segmentDistance: Int,
    isReached: Boolean,
    isCurrent: Boolean,
    showMiles: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            // Fading out indication for stops covered
            .alpha(if (isReached) 0.5f else 1f),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isCurrent -> MaterialTheme.colorScheme.primaryContainer
                isReached -> MaterialTheme.colorScheme.surfaceVariant
                else -> MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = if (isReached) MaterialTheme.colorScheme.primary else Color.Transparent,
                modifier = Modifier.size(24.dp).padding(end = 8.dp)
            )

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stop.name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (isCurrent) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
                    )
                    // Visa-required ka indicator
                    if (stop.visaRequired) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Visa Required",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .size(16.dp)
                                .padding(start = 4.dp)
                        )
                    }
                }
                Text(
                    text = "Segment: ${formatDistance(segmentDistance, showMiles)}",
                    style = MaterialTheme.typography.bodyMedium
                )
//                 Optionally, text used for visa indiacation niche
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
