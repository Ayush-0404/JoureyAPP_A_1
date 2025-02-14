package com.example.myapplication

import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState


//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Home
//import androidx.compose.material.icons.filled.Settings
//import androidx.compose.material3.Icon
//import androidx.compose.material3.NavigationBar
//import androidx.compose.material3.NavigationBarItem
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.navigation.NavController
//import androidx.navigation.compose.currentBackStackEntryAsState
//
//@Composable
//fun BottomNavigationBar(navController: NavController) {
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry?.destination?.route
//
//    NavigationBar {
//        NavigationBarItem(
//            icon = { Icon(Icons.Default.Home, "Home") },
//            label = { Text("Progress") },
//            selected = currentRoute == "journeyProgress",
//            onClick = { navController.navigate("journeyProgress") }
//        )
//        NavigationBarItem(
//            icon = { Icon(Icons.Default.Settings, "Settings") },
//            label = { Text("Settings") },
//            selected = currentRoute == "settings",
//            onClick = { navController.navigate("settings") }
//        )
//    }
//}