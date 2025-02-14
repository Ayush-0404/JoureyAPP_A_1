package com.example.myapplication

data class Stop (
    val name: String,
    val distanceFromLast: Int,
    val visaRequired: Boolean,
    var reached: Boolean = false
){
    fun getDist(isMiles: Boolean): String{
        val distance = if (isMiles) distanceFromLast * 0.621371 else distanceFromLast.toDouble()
        val unit = if (isMiles) "mi" else "km"
        return String.format("%.1f %s" ,distance,unit)
    }
}