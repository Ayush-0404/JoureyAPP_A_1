package com.example.travellercompose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.journeytrackercompose.StartScreen

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
//@Composable
//fun AppNavigation(navController: NavHostController, stops: List<Stop>) {
//    NavHost(navController, startDestination = "startScreen") {
//        composable("startScreen") {
//            StartScreen(navController)
//        }
//        composable("routeSelectionScreen/{start}/{destination}") { backStackEntry ->
//            val start = backStackEntry.arguments?.getString("start") ?: ""
//            val destination = backStackEntry.arguments?.getString("destination") ?: ""
//            RouteSelectionScreen(navController, start, destination)
//        }
//        composable("journeyScreen/{start}/{destination}/{route}") { backStackEntry ->
//            val start = backStackEntry.arguments?.getString("start") ?: ""
//            val destination = backStackEntry.arguments?.getString("destination") ?: ""
//            val route = backStackEntry.arguments?.getString("route") ?: ""
//            JourneyScreen(navController, start, destination, route, stops)
//        }
//    }
//}