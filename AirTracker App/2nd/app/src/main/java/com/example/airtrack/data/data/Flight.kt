package com.example.airtrack.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "flights")
data class Flight(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "flight_number") val flightNumber: String,
    @ColumnInfo(name = "origin") val origin: String,
    @ColumnInfo(name = "destination") val destination: String,
    @ColumnInfo(name = "scheduled_departure") val scheduledDeparture: Long,
    @ColumnInfo(name = "actual_departure") val actualDeparture: Long,
    @ColumnInfo(name = "scheduled_arrival") val scheduledArrival: Long,
    @ColumnInfo(name = "actual_arrival") val actualArrival: Long,
    @ColumnInfo(name = "date_added") val dateAdded: String = LocalDate.now().toString()
)