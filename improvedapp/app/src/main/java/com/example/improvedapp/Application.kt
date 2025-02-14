package com.example.improvedapp

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Application(stops: List<Stop>) {
    var currentStopIndex by remember { mutableStateOf(0) }
    var currentUnit by remember { mutableStateOf(DistanceUnit.Km) }
    val context = LocalContext.current

    val totalDistance = stops.sumOf { it.distanceFromPrevious }
    val coveredDistance = stops.subList(0, currentStopIndex + 1).sumOf { it.distanceFromPrevious }
    val remainingDistance = totalDistance - coveredDistance

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        // Header with navigation
        JourneyHeader(context)

        Spacer(modifier = Modifier.height(10.dp))

        // Distance Progress and Controls
        Text(text = "Distance Covered: ${convertDistance(coveredDistance, currentUnit)}", fontSize = 24.sp)
        LinearProgressIndicator(
            progress = { (coveredDistance / totalDistance).toFloat() },
            modifier = Modifier.fillMaxWidth().height(30.dp).padding(vertical = 12.dp),
        )
        Text(text = "Remaining Distance: ${convertDistance(remainingDistance, currentUnit)}", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(20.dp))

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { currentUnit = toggleUnit(currentUnit) }, modifier = Modifier.width(145.dp)) {
                Text(text = "Switch to ${if (currentUnit == DistanceUnit.Km) "Miles" else "KM"}")
            }

            Button(
                onClick = { if (currentStopIndex < stops.lastIndex) currentStopIndex++ },
                enabled = currentStopIndex < stops.lastIndex,
                modifier = Modifier.width(140.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (currentStopIndex == stops.lastIndex) Color.Gray else MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Next Stop")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        StopList(stops, currentStopIndex, currentUnit)
    }
}

@Composable
fun JourneyHeader(context: android.content.Context) {
    Box(
        modifier = Modifier.fillMaxWidth().height(60.dp).background(Color(0xFF6A0DAD))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                fontSize = 28.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp).clickable {
                    context.startActivity(Intent(context, RouteSelectionActivity::class.java))
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Journey Progress",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
