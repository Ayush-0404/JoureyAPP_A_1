//package com.example.journeytrackercompose
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.Button
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import androidx.compose.material.*
//import androidx.compose.material3.MaterialTheme
//
//import androidx.compose.ui.text.input.TextFieldValue
//
//@Composable
//fun StartScreen(navController: NavController) {
//    var startLocation by remember { mutableStateOf(TextFieldValue("")) }
//    var destination by remember { mutableStateOf(TextFieldValue("")) }
//
//    Column(
//        modifier = Modifier.fillMaxSize().padding(16.dp)
//    ) {
//        Text(text = "Enter Your Journey Details", style = MaterialTheme.typography.titleLarge)
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        OutlinedTextField(
//            value = startLocation,
//            onValueChange = { startLocation = it },
//            label = { Text("Start Location") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        OutlinedTextField(
//            value = destination,
//            onValueChange = { destination = it },
//            label = { Text("Destination") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = {
//                // In Button onClick:
//                navController.navigate("journeyScreen/${startLocation.text}/${destination.text}")
//            }
//
//    }
//}
package com.example.journeytrackercompose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.MaterialTheme

@Composable
fun StartScreen(navController: NavController) {
    var startLocation by remember { mutableStateOf(TextFieldValue("")) }
    var destination by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Enter Your Journey Details",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = startLocation,
            onValueChange = { startLocation = it },
            label = { Text("Start Location") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text("Destination") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (startLocation.text.isNotBlank() && destination.text.isNotBlank()) {
                    navController.navigate(
                        "journeyScreen/${startLocation.text}/${destination.text}"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Start Journey")
        }
    }
}