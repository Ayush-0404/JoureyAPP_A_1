import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FlightViewModel : ViewModel() {
    private val _flightData = MutableLiveData<FlightData>()
    val flightData: LiveData<FlightData> = _flightData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val apiService: FlightApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://api.aviationstack.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlightApiService::class.java)
    }

    fun fetchFlightData(flightNumber: String) {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val response = apiService.getFlightData(
                    BuildConfig.AVIATION_STACK_API_KEY,
                    flightNumber
                )

                handleResponse(response)
            } catch (e: Exception) {
                _errorMessage.postValue("Network error: ${e.message}")
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    private fun handleResponse(response: Response<FlightResponse>) {
        if (response.isSuccessful) {
            val body = response.body()
            if (body?.data?.isNotEmpty() == true) {
                _flightData.postValue(body.data[0])
            } else {
                _errorMessage.postValue("No flight data available")
            }
        } else {
            _errorMessage.postValue("API error: ${response.code()}")
        }
    }
}

// API Response Classes
data class FlightResponse(
    val data: List<FlightData>
)

data class FlightData(
    val live: LiveData?,
    val departure: DepartureArrival?,
    val arrival: DepartureArrival?,
    val flight: FlightInfo?
)

data class LiveData(
    val latitude: Double?,
    val longitude: Double?,
    val altitude: Double?
)

data class DepartureArrival(
    val airport: String?,
    val estimated: String?,
    val delay: Int?
)

data class FlightInfo(
    val status: String?
)