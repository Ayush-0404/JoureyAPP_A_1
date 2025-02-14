package com.example.improvedapp


fun convertDistance(distanceKm: Double, unit: DistanceUnit): String {
    return if (unit == DistanceUnit.Miles) {
        "%.1f Miles".format(distanceKm * 0.621371)
    } else {
        "%.1f Km".format(distanceKm)
    }
}

fun toggleUnit(currentUnit: DistanceUnit): DistanceUnit {
    return if (currentUnit == DistanceUnit.Km) DistanceUnit.Miles else DistanceUnit.Km
}
