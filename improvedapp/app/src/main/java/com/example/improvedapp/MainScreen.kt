package com.example.improvedapp
//
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun MainScreen(stops: List<Stop>) {
//    var coveredDistance by remember { mutableStateOf(0.0) }
//    var remainingDistance by remember { mutableStateOf(stops.sumOf { it.distance }) }
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "Journey Progress", style = MaterialTheme.typography.headlineSmall)
//
//        // Distance Information
//        Text(text = "Distance Covered: $coveredDistance Km")
//        Text(text = "Remaining Distance: $remainingDistance Km")
//
//        // Progress Bar
//        LinearProgressIndicator(
//            progress = if (coveredDistance + remainingDistance > 0) {
//                (coveredDistance / (coveredDistance + remainingDistance)).toFloat()
//            } else 0f,
//            modifier = Modifier.fillMaxWidth().padding(8.dp)
//        )
//
//        // Buttons Row
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Button(onClick = { /* Toggle KM <-> Miles */ }) {
//                Text("Switch to Miles")
//            }
//            Button(onClick = {
//                if (remainingDistance > 0) {
//                    val nextStop = stops.firstOrNull { it.distance > coveredDistance }
//                    nextStop?.let {
//                        coveredDistance += it.distance
//                        remainingDistance -= it.distance
//                    }
//                }
//            }) {
//                Text("Next Stop")
//            }
//            Button(onClick = {
//                // Reset Journey Progress
//                coveredDistance = 0.0
//                remainingDistance = stops.sumOf { it.distance }
//            }) {
//                Text("Reset Journey")
//            }
//        }
//
//        // Scrollable LazyColumn for stops
//        LazyColumn(
//            modifier = Modifier.fillMaxSize().padding(top = 16.dp)
//        ) {
//            items(stops) { stop ->
//                Card(
//                    modifier = Modifier.fillMaxWidth().padding(8.dp),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(text = stop.name, style = MaterialTheme.typography.titleMedium)
//                        Text(text = "Visa Required: ${if (stop.requiresVisa) "Yes" else "No"}")
//                        Text(text = "Distance: ${stop.distance} Km")
//                    }
//                }
//            }
//        }
//    }
//}
