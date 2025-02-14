package com.example.journeycompose1

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavigation(navController: NavHostController, stops: List<Stop>) {
    NavHost(navController, startDestination = "startScreen") {
        composable("startScreen") {
            StartScreen(navController)
        }
        composable("journeyScreen/{start}/{destination}") { backStackEntry ->
            val start = backStackEntry.arguments?.getString("start") ?: ""
            val destination = backStackEntry.arguments?.getString("destination") ?: ""
            JourneyScreen(navController, start, destination, stops)
        }
    }
}