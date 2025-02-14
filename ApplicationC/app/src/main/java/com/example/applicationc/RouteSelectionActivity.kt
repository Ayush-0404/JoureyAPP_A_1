package com.example.applicationc


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.applicationc.ui.theme.ApplicationCTheme
import com.example.applicationc.Stop
import java.io.BufferedReader
import java.io.InputStreamReader

class RouteSelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationCTheme {
                RouteSelectionScreen(
                    onRouteSelected = { stops ->
                        startActivity(
                            Intent(this, MainActivity::class.java).apply {
                                putExtra("SELECTED_STOPS", ArrayList(stops))
                            }
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteSelectionScreen(onRouteSelected: (List<Stop>) -> Unit) {
    val context = LocalContext.current
    val allRoutes = remember { loadAllRoutes(context) }
    var start by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var filteredRoutes by remember { mutableStateOf<List<Stop>?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Route") },
                navigationIcon = {
                    IconButton(onClick = { context.startActivity(Intent(context, MainActivity::class.java)) }) {
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
            OutlinedTextField(
                value = start,
                onValueChange = { start = it },
                label = { Text("Start City") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = destination,
                onValueChange = { destination = it },
                label = { Text("Destination City") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    filteredRoutes = allRoutes.filterRoute(start, destination)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Find Routes")
            }

            Spacer(Modifier.height(24.dp))

            when {
                filteredRoutes == null -> {
                    // Initial state
                }
                filteredRoutes!!.isEmpty() -> {
                    Text(
                        text = "No routes found between $start and $destination",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                else -> {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(filteredRoutes!!.windowed(2, 1, partialWindows = true)) { pair ->
                            RouteCard(
                                start = pair[0],
                                destination = pair[1],
                                onSelect = { onRouteSelected(filteredRoutes!!) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RouteCard(start: Stop, destination: Stop, onSelect: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onSelect),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${start.name} → ${destination.name}",
                style = MaterialTheme.typography.titleMedium
            )
            Text("Distance: ${start.distanceKm} km")
            Text("Visa required: ${if (start.visaRequired) "Yes" else "No"}")
        }
    }
}

private fun loadAllRoutes(context: Context): List<Stop> {
    return try {
        context.assets.open("stops.txt").bufferedReader().useLines { lines ->
            lines.mapNotNull { line ->
                val parts = line.split(",")
                if (parts.size == 3) Stop(
                    parts[0].trim(),
                    parts[1].trim().toInt(),
                    parts[2].trim().toBooleanStrict()
                ) else null
            }.toList()
        }
    } catch (e: Exception) {
        emptyList()
    }
}

private fun List<Stop>.filterRoute(start: String, destination: String): List<Stop> {
    val startIndex = indexOfFirst { it.name.equals(start, true) }
    val destIndex = indexOfLast { it.name.equals(destination, true) }
    if (startIndex == -1 || destIndex == -1 || startIndex >= destIndex) return emptyList()
    return subList(startIndex, destIndex + 1)
}