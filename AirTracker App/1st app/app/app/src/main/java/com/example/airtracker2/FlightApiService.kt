import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlightApiService {
    @GET("v1/flights")
    suspend fun getFlightData(
        @Query("access_key") apiKey: String,
        @Query("flight_iata") flightNumber: String
    ): Response<FlightResponse>
}

data class FlightResponse(
    val data: List<FlightData>
)

data class FlightData(
    val live: LiveData?,
    val departure: DepartureArrival?,
    val arrival: DepartureArrival?
)

data class LiveData(
    val latitude: Double?,
    val longitude: Double?,
    val altitude: Double?
)

data class DepartureArrival(
    val airport: String?,
    val estimated: String? // Timestamp for delays
)