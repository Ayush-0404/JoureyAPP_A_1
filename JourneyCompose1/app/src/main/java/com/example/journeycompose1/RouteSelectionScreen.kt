//package com.example.improvedapp
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//@Composable
//fun RouteSelectionScreen(
//    availableRoutes: Map<Int, List<Stop>>,
//    onRouteSelected: (List<Stop>) -> Unit
//) {
//    var startLocation by remember { mutableStateOf(TextFieldValue("")) }
//    var destination by remember { mutableStateOf(TextFieldValue("")) }
//    var filteredRoutes by remember { mutableStateOf<Map<Int, List<Stop>>>(emptyMap()) }
//    var routeNotFound by remember { mutableStateOf(false) }
//
//    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//        Text(text = "Enter Journey Details", style = MaterialTheme.typography.headlineMedium)
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        OutlinedTextField(
//            value = startLocation,
//            onValueChange = { startLocation = it },
//            label = { Text("Starting Location") },
//            modifier = Modifier.fillMaxWidth(),
//            keyboardOptions = KeyboardOptions(autoCorrect = false)
//        )
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        OutlinedTextField(
//            value = destination,
//            onValueChange = { destination = it },
//            label = { Text("Destination Location") },
//            modifier = Modifier.fillMaxWidth(),
//            keyboardOptions = KeyboardOptions(autoCorrect = false)
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Button(
//            onClick = {
//                val routes = filterRoutes(startLocation.text, destination.text, availableRoutes)
//                filteredRoutes = routes
//                routeNotFound = routes.isEmpty()
//            },
//            modifier = Modifier.fillMaxWidth().height(55.dp)
//        ) {
//            Text("Find Routes", fontSize = 20.sp)
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        if (routeNotFound) {
//            Text(
//                text = "Route Not Available",
//                color = MaterialTheme.colorScheme.error,
//                style = MaterialTheme.typography.titleMedium
//            )
//        } else {
//            LazyColumn {
//                items(filteredRoutes.keys.toList()) { index ->
//                    RouteOptionItem(index, filteredRoutes[index]!!) {
//                        onRouteSelected(filteredRoutes[index]!!)
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun RouteOptionItem(routeIndex: Int, route: List<Stop>, onClick: () -> Unit) {
//    Card(
//        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { onClick() },
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = "Stops: ${route.joinToString(" → ") { it.name }}")
//            Text(text = "Total Distance: ${route.sumOf { it.distanceFromPrevious }} Km")
//        }
//    }
//}
//
//fun filterRoutes(start: String, end: String, availableRoutes: Map<Int, List<Stop>>): Map<Int, List<Stop>> {
//    return availableRoutes.filter { (_, routeStops) ->
//        val firstStop = routeStops.first().name.equals(start, ignoreCase = true)
//        val lastStop = routeStops.last().name.equals(end, ignoreCase = true)
//        firstStop && lastStop
//    }
//}
