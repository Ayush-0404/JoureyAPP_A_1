package com.example.airtrack.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AviationStackApi {
    @GET("flights")
    suspend fun getFlights(
        @Query("access_key") apiKey: String = "f4fdf83213c56e9077383a45d03ae1c2",
        @Query("dep_iata") depIata: String,
        @Query("arr_iata") arrIata: String,
        @Query("flight_date") flightDate: String
    ): Response<FlightResponse>
}

data class FlightResponse(val data: List<FlightData>)

data class FlightData(
    val flight: FlightNumber,
    val departure: Departure,
    val arrival: Arrival
)

data class FlightNumber(val iata: String)
data class Departure(
    val iata: String,
    val scheduled: String,
    val actual: String?
)
data class Arrival(
    val iata: String,
    val scheduled: String,
    val actual: String?
)