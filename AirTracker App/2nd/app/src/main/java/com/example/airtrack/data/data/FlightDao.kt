package com.example.airtrack.data
// File: app/src/main/java/com/example/airtracker222/data/FlightDao.kt

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FlightDao {
    @Insert
    suspend fun insertFlight(flight: Flight)

    @Query("""
        SELECT origin, destination, 
        AVG(actual_arrival - actual_departure) AS avgDuration 
        FROM flights 
        WHERE date_added >= :startDate 
        GROUP BY origin, destination
    """)
    suspend fun getRouteAverages(startDate: String): List<RouteAverage>
}

data class RouteAverage(
    val origin: String,
    val destination: String,
    val avgDuration: Double
)