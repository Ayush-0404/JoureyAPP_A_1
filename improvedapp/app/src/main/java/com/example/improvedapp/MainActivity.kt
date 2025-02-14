package com.example.improvedapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import com.example.improvedapp.ui.theme.AppTheme
import com.example.improvedapp.ui.theme.ImprovedappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve the selected stops passed from the route selection screen.
        val selectedStops = intent.getParcelableArrayListExtra<Stop>("SELECTED_STOPS")
        if (selectedStops.isNullOrEmpty()) {
            finish()
            return
        }
        setContent {
            ImprovedappTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // MainScreen displays the journey progress and stops.
                    MainScreen(selectedStops)
                }
            }
        }
    }
}
@Composable
fun MainScreen(stops: List<Stop>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Application(stops)
    }
}

