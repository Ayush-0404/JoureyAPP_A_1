import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.airtracker2.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FlightViewModel
    private val handler = Handler(Looper.getMainLooper())
    private var isTracking = false

    // Date formatters
    private val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val delayFormatter = SimpleDateFormat("mm 'min'", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[FlightViewModel::class.java]

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewModel.flightData.observe(this) { data ->
            data?.let { updateFlightUI(it) }
            binding.cardFlightData.isVisible = data != null
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.isVisible = isLoading
            binding.btnTrack.isEnabled = !isLoading
        }

        viewModel.errorMessage.observe(this) { error ->
            binding.tvError.text = error
            binding.tvError.isVisible = error != null
        }
    }

    private fun setupClickListeners() {
        binding.btnTrack.setOnClickListener {
            val flightNumber = binding.etFlightNumber.text.toString().trim()

            if (validateFlightNumber(flightNumber)) {
                toggleTracking(flightNumber)
            } else {
                showError("Invalid flight number format (e.g., AA123)")
            }
        }
    }

    private fun validateFlightNumber(input: String): Boolean {
        return input.matches(Regex("[A-Za-z]{2,3}\\d{1,4}"))
    }

    private fun toggleTracking(flightNumber: String) {
        isTracking = !isTracking
        binding.btnTrack.text = if (isTracking) "Stop Tracking" else "Track Flight"

        if (isTracking) {
            startTracking(flightNumber)
        } else {
            stopTracking()
        }
    }

    private fun startTracking(flightNumber: String) {
        handler.postDelayed(object : Runnable {
            override fun run() {
                if (isTracking) {
                    viewModel.fetchFlightData(flightNumber)
                    handler.postDelayed(this, 60000) // 60 seconds
                }
            }
        }, 0)
    }

    private fun stopTracking() {
        handler.removeCallbacksAndMessages(null)
        binding.cardFlightData.isVisible = false
    }

    private fun updateFlightUI(data: FlightData) {
        // Position Data
        data.live?.let {
            binding.tvLatitude.text = formatCoordinate(it.latitude, "N", "S")
            binding.tvLongitude.text = formatCoordinate(it.longitude, "E", "W")
            binding.tvAltitude.text = "${it.altitude?.toInt() ?: 0} ft"
        }

        // Departure/Arrival Data
        data.departure?.let {
            binding.tvDeparture.text = "Departure: ${formatAirportInfo(it)}"
        }

        data.arrival?.let {
            binding.tvArrival.text = "Arrival: ${formatAirportInfo(it)}"
        }

        // Status
        val status = data.flight?.status ?: "Unknown"
        binding.tvStatus.text = "Status: $status"
        binding.tvStatus.setTextColor(
            when (status.toLowerCase(Locale.ROOT)) {
                "active" -> getColor(R.color.status_default)
                "landed", "cancelled" -> getColor(R.color.error_red)
                else -> getColor(R.color.secondary)
            }
        )
    }

    private fun formatCoordinate(value: Double?, posDir: String, negDir: String): String {
        return value?.let {
            val absValue = Math.abs(it)
            val direction = if (it >= 0) posDir else negDir
            "%.4f° $direction".format(absValue)
        } ?: "N/A"
    }

    private fun formatAirportInfo(info: DepartureArrival): String {
        val airport = info.airport ?: "Unknown"
        val delay = info.delay?.let {
            if (it > 0) " (Delay: $it min)" else ""
        } ?: ""
        return "$airport$delay"
    }

    private fun showError(message: String) {
        binding.tvError.text = message
        binding.tvError.isVisible = true
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}