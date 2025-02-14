package com.example.applicationc
import android.os.Parcel
import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class Stop(
    val name: String,
    val distanceKm: Int,
    val visaRequired: Boolean
) : Parcelable {
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        TODO("Not yet implemented")
    }
}