package com.example.myapplication

//
//import android.content.Intent
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.animation.core.animateFloatAsState
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.itemsIndexed
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.StrokeCap
//import androidx.compose.ui.unit.dp
//import androidx.core.content.ContextCompat.startActivity
//import com.example.myapplication.ui.theme.MyApplicationTheme
//
//class JourneyActivity : ComponentActivity() {
//    private val sampleStops = listOf(
//        Stop("New Delhi", true, 0.0),
//        Stop("Dubai", true, 2500.0),
//        Stop("London", true, 8000.0)
//    )
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MyApplicationTheme{
//                JourneyProgressScreen(stops = sampleStops)
//            }
//        }
//    }
//}
//
//@Composable
//fun JourneyProgressScreen(stops: List<Stop>) {
//    var currentStopIndex by remember { mutableStateOf(0) }
//    val totalDistance = stops.sumOf { it.distanceFromPrevious }
//    val coveredDistance = stops.take(currentStopIndex + 1).sumOf { it.distanceFromPrevious }
//    val progress by animateFloatAsState(
//        targetValue = (coveredDistance / totalDistance).toFloat(),
//        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
//    )
//
//    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = { Text("Journey Tracker") },
//                navigationIcon = {
//                    IconButton(onClick = {
//                        startActivity(Intent(LocalContext.current, RouteSelectionActivity::class.java))
//                    }) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                },
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.primary
//                )
//            )
//        }
//    ) { padding ->
//        Column(
//            modifier = Modifier
//                .padding(padding)
//                .fillMaxSize()
//                .padding(16.dp)
//        ) {
//            // Progress Section
//            CircularProgressIndicator(
//                progress = progress,
//                modifier = Modifier
//                    .size(120.dp)
//                    .align(Alignment.CenterHorizontally),
//                strokeWidth = 8.dp,
//                strokeCap = StrokeCap.Round,
//                color = MaterialTheme.colorScheme.primary
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Distance Metrics
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                DistanceMetric("Covered", coveredDistance)
//                DistanceMetric("Remaining", totalDistance - coveredDistance)
//                DistanceMetric("Total", totalDistance)
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Stops List
//            LazyColumn {
//                itemsIndexed(stops) { index, stop ->
//                    StopCard(
//                        stop = stop,
//                        isCurrent = index == currentStopIndex,
//                        isPast = index < currentStopIndex,
//                        modifier = Modifier.padding(vertical = 8.dp)
//                    )
//                }
//            }
//
//            // Control Button
//            Button(
//                onClick = {
//                    if (currentStopIndex < stops.lastIndex) currentStopIndex++
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 16.dp),
//                enabled = currentStopIndex < stops.lastIndex
//            ) {
//                Text(if (currentStopIndex == stops.lastIndex) "Journey Complete" else "Next Stop")
//            }
//        }
//    }
//}
//@Composable
//fun DistanceMetric(label: String, distance: Double) {
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Text(
//            text = "%.1f Km".format(distance),
//            style = MaterialTheme.typography.titleMedium,
//            color = MaterialTheme.colorScheme.primary
//        )
//        Text(
//            text = label,
//            style = MaterialTheme.typography.labelMedium,
//            color = MaterialTheme.colorScheme.onSurfaceVariant
//        )
//    }
//}
//
//@Composable
//fun StopCard(stop: Stop, isCurrent: Boolean, isPast: Boolean, modifier: Modifier = Modifier) {
//    Card(
//        modifier = modifier,
//        colors = CardDefaults.cardColors(
//            containerColor = when {
//                isCurrent -> MaterialTheme.colorScheme.primaryContainer
//                isPast -> MaterialTheme.colorScheme.surfaceVariant
//                else -> MaterialTheme.colorScheme.surface
//            }
//        )
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Column(modifier = Modifier.weight(1f)) {
//                Text(
//                    text = stop.name,
//                    style = MaterialTheme.typography.titleMedium,
//                    color = MaterialTheme.colorScheme.primary
//                )
//                Text(
//                    text = "Visa required: ${if (stop.visaRequired) "Yes" else "No"}",
//                    style = MaterialTheme.typography.bodySmall
//                )
//                Text(
//                    text = "Distance: ${stop.distanceFromPrevious} Km",
//                    style = MaterialTheme.typography.bodySmall
//                )
//            }
//            if (isCurrent) {
//                Icon(
//                    imageVector = Icons.Default.Navigation,
//                    contentDescription = "Current Stop",
//                    tint = MaterialTheme.colorScheme.primary
//                )
//            }
//        }
//    }
//}