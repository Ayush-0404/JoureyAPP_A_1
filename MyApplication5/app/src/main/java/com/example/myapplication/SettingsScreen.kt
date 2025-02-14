package com.example.myapplication
//
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//
//@Composable
//fun SettingsScreen() {
//    var darkModeEnabled by remember { mutableStateOf(false) }
//    var selectedUnit by remember { mutableStateOf(DistanceUnit.Km) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text(
//            text = "Preferences",
//            style = MaterialTheme.typography.headlineMedium,
//            modifier = Modifier.padding(bottom = 16.dp)
//        )
//
//        SwitchPreference(
//            checked = darkModeEnabled,
//            onCheckedChange = { darkModeEnabled = it },
//            label = "Dark Mode"
//        )
//
//        UnitSelector(
//            selectedUnit = selectedUnit,
//            onUnitSelected = { selectedUnit = it }
//        )
//    }
//}
//
//@Composable
//fun SwitchPreference(
//    checked: Boolean,
//    onCheckedChange: (Boolean) -> Unit,
//    label: String
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Text(text = label, modifier = Modifier.weight(1f))
//        Switch(checked = checked, onCheckedChange = onCheckedChange)
//    }
//}
//
//
//@Composable
//fun UnitSelector(selectedUnit: DistanceUnit, onUnitSelected: (DistanceUnit) -> Unit) {
//    var expanded by remember { mutableStateOf(false) }
//
//    Box(modifier = Modifier.fillMaxWidth()) {
//        OutlinedButton(
//            onClick = { expanded = true },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Distance Unit: ${selectedUnit.name}")
//        }
//
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            DistanceUnit.values().forEach { unit ->
//                DropdownMenuItem(
//                    text = { Text(unit.name) },
//                    onClick = {
//                        onUnitSelected(unit)
//                        expanded = false
//                    }
//                )
//            }
//        }
//    }
//}