package com.example.myapplication

data class Stop(
    val name: String,
    val distanceKm: Int,
    val visaRequirement: String,
    var isReached: Boolean = false
) {
    fun getDistanceInMiles(): String {
        return String.format("%.1f", distanceKm * 0.621371)
    }
}
