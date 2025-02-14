package com.example.applicationc

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.applicationc.Stop

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JourneyScreen(
    start: String,
    destination: String,
    allStops: List<Stop>
) {
    val startStop = allStops.firstOrNull { it.name.equals(start, true) }
    val destStop = allStops.firstOrNull { it.name.equals(destination, true) }

    if (startStop == null || destStop == null || allStops.indexOf(startStop) >= allStops.indexOf(destStop)) {
        ErrorScreen(message = "Invalid journey parameters")
        return
    }

    val routeStops = allStops.subList(allStops.indexOf(startStop), allStops.indexOf(destStop) + 1)
    var currentStopIndex by remember { mutableStateOf(0) }
    var showMiles by remember { mutableStateOf(false) }

    val totalDistance = routeStops.sumOf { it.distanceKm }
    val distanceCovered = routeStops.take(currentStopIndex).sumOf { it.distanceKm }
    val distanceLeft = totalDistance - distanceCovered

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Journey Progress") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back */ }) {
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
            // Progress Section
            JourneyProgress(
                covered = distanceCovered,
                remaining = distanceLeft,
                total = totalDistance,
                showMiles = showMiles,
                onUnitToggle = { showMiles = !showMiles }
            )

            // Stops List
            if (routeStops.size > 3) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    itemsIndexed(routeStops) { index, stop ->
                        StopItem(
                            stop = stop,
                            isReached = index < currentStopIndex,
                            isCurrent = index == currentStopIndex,
                            showMiles = showMiles,
                            segmentDistance = if (index == 0) 0 else stop.distanceKm - routeStops[index - 1].distanceKm
                        )
                    }
                }
            } else {
                Column(modifier = Modifier.weight(1f)) {
                    routeStops.forEachIndexed { index, stop ->
                        StopItem(
                            stop = stop,
                            isReached = index < currentStopIndex,
                            isCurrent = index == currentStopIndex,
                            showMiles = showMiles,
                            segmentDistance = if (index == 0) 0 else stop.distanceKm - routeStops[index - 1].distanceKm
                        )
                    }
                }
            }

            // Controls
            JourneyControls(
                currentIndex = currentStopIndex,
                totalStops = routeStops.size,
                onNext = { if (currentStopIndex < routeStops.lastIndex) currentStopIndex++ },
                onReset = { currentStopIndex = 0 }
            )
        }
    }
}

@Composable
private fun JourneyProgress(
    covered: Int,
    remaining: Int,
    total: Int,
    showMiles: Boolean,
    onUnitToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            LinearProgressIndicator(
                progress = if (total > 0) covered.toFloat() / total else 0f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Covered: ${formatDistance(covered, showMiles)}")
                Text("Remaining: ${formatDistance(remaining, showMiles)}")
            }

            Button(
                onClick = onUnitToggle,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(if (showMiles) "Show km" else "Show miles")
            }
        }
    }
}

@Composable
private fun StopItem(
    stop: Stop,
    isReached: Boolean,
    isCurrent: Boolean,
    showMiles: Boolean,
    segmentDistance: Int
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
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stop.name,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isCurrent) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface
            )
            Text("Distance: ${formatDistance(segmentDistance, showMiles)}")
            Text("Visa required: ${if (stop.visaRequired) "Yes" else "No"}")
        }
    }
}

@Composable
private fun JourneyControls(
    currentIndex: Int,
    totalStops: Int,
    onNext: () -> Unit,
    onReset: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onReset,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Text("Reset Journey")
        }

        Button(
            onClick = onNext,
            enabled = currentIndex < totalStops - 1
        ) {
            Text(if (currentIndex == totalStops - 2) "Final Stop" else "Next Stop")
        }
    }
}

private fun formatDistance(km: Int, miles: Boolean): String {
    return if (miles) "${(km * 0.621371).toInt()} mi" else "$km km"
}

@Composable
private fun ErrorScreen(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}