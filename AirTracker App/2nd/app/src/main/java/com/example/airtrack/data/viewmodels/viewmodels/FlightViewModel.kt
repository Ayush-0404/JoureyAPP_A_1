package com.example.airtrack.viewmodels

// File: app/src/main/java/com/example/airtracker222/viewmodels/FlightViewModel.kt

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.airtrack.data.RouteAverage
import com.example.airtrack.repository.FlightRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FlightViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FlightRepository(application)
    private val _averages = MutableStateFlow<List<RouteAverage>>(emptyList())
    val averages: StateFlow<List<RouteAverage>> = _averages

    init {
        loadAverages()
    }

    fun loadAverages() {
        viewModelScope.launch {
            _averages.value = repository.getRouteAverages()
        }
    }
}