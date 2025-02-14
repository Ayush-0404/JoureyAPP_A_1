package com.example.travellercompose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*


@Composable
fun RouteSelectionScreen(navController: NavController, start: String, destination: String) {
    val possibleRoutes = listOf(
        "Route A - Via Frankfurt",
        "Route B - Via Dubai",
        "Route C - Via Singapore"
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Select a Route", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(possibleRoutes) { route ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            navController.navigate("journeyScreen/$start/$destination/$route")
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = route,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
