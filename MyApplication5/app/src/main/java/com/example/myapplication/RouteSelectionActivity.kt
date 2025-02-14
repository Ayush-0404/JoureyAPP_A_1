package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
//
//class RouteSelectionActivity : ComponentActivity() {
//    private val sampleRoutes = listOf(
//        Route(1, "New Delhi → Dubai → London", 11500.0, 3),
//        Route(2, "Mumbai → Paris → New York", 9200.0, 3),
//        Route(3, "Singapore → Tokyo → Sydney", 7800.0, 3)
//    )
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MyApplicationTheme{
//                RouteSelectionScreen(
//                    routes = sampleRoutes,
//                    onRouteSelected = { route ->
//                        val intent = Intent(this, JourneyActivity::class.java)
//                        startActivity(intent)
//                    }
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun RouteSelectionScreen(
//    routes: List<Route>,
//    onRouteSelected: (Route) -> Unit
//) {
//    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        // Search Bar
//        OutlinedTextField(
//            value = searchQuery,
//            onValueChange = { searchQuery = it },
//            label = { Text("Search Routes") },
//            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(16.dp)
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Route List
//        LazyColumn {
//            items(routes) { route ->
//                RouteCard(
//                    route = route,
//                    modifier = Modifier.padding(vertical = 8.dp),
//                    onClick = { onRouteSelected(route) }
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun RouteCard(route: Route, modifier: Modifier = Modifier, onClick: () -> Unit) {
//    Card(
//        modifier = modifier
//            .fillMaxWidth()
//            .clickable { onClick() },
//        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surfaceVariant
//        )
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(
//                text = route.name,
//                style = MaterialTheme.typography.titleMedium,
//                color = MaterialTheme.colorScheme.primary
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            RouteInfoItem("Total Distance", "${route.totalDistance} Km")
//            RouteInfoItem("Stops", route.stopsCount.toString())
//        }
//    }
//}
//
//@Composable
//fun RouteInfoItem(label: String, value: String) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(text = label, color = MaterialTheme.colorScheme.onSurfaceVariant)
//        Text(text = value, color = MaterialTheme.colorScheme.primary)
//    }
//}
//
//data class Route(
//    val id: Int,
//    val name: String,
//    val totalDistance: Double,
//    val stopsCount: Int
//)