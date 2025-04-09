package com.example.airtrack.repository

// File: app/src/main/java/com/example/airtracker222/repository/FlightRepository.kt

import android.content.Context
import com.example.airtrack.data.AppDatabase
import com.example.airtrack.data.RouteAverage
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FlightRepository(private val context: Context) {
    private val db = AppDatabase.getDatabase(context)

    suspend fun getRouteAverages(): List<RouteAverage> {
        return try {
            val calendar = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -7) }
            val startDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
            db.flightDao().getRouteAverages(startDate) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}