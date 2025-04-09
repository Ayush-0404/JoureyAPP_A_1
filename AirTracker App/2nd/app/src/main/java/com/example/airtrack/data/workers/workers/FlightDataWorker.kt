package com.example.airtrack.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.airtrack.data.AppDatabase
import com.example.airtrack.data.Flight
import com.example.airtrack.network.FlightData
import com.example.airtrack.network.RetrofitClient
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class FlightDataWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val routes = listOf("DEL" to "BOM", "BOM" to "BLR") //
            routes.forEach { (origin, destination) ->
                val flights = fetchFlights(origin, destination)
                flights.take(3).forEach { flightData ->
                    saveFlightToDatabase(flightData, origin, destination)
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }


    private suspend fun fetchFlights(origin: String, destination: String): List<FlightData> {
        return try {
            val response = RetrofitClient.instance.getFlights(
                depIata = origin,
                arrIata = destination,
                flightDate = LocalDateTime.now().toString().substring(0, 10)
            )
            response.body()?.data ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private suspend fun saveFlightToDatabase(
        data: FlightData,
        origin: String,
        destination: String
    ) {
        val db = AppDatabase.getDatabase(applicationContext)
        val flight = Flight(
            flightNumber = data.flight.iata,
            origin = origin,
            destination = destination,
            scheduledDeparture = parseDateTime(data.departure.scheduled),
            actualDeparture = parseDateTime(data.departure.actual ?: data.departure.scheduled),
            scheduledArrival = parseDateTime(data.arrival.scheduled),
            actualArrival = parseDateTime(data.arrival.actual ?: data.arrival.scheduled)
        )
        db.flightDao().insertFlight(flight)
    }

    private fun parseDateTime(dateTimeStr: String): Long {
        return try {
            LocalDateTime.parse(
                dateTimeStr,
                DateTimeFormatter.ISO_OFFSET_DATE_TIME
            ).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        } catch (e: Exception) {
            System.currentTimeMillis()
        }
    }
}