package com.example.improvedapp

import android.os.Parcel
import android.os.Parcelable

data class Stop(
    val name: String,
    val visaRequired: Boolean,
    val distanceFromPrevious: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeByte(if (visaRequired) 1 else 0)
        parcel.writeDouble(distanceFromPrevious)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Stop> {
        override fun createFromParcel(parcel: Parcel): Stop = Stop(parcel)
        override fun newArray(size: Int): Array<Stop?> = arrayOfNulls(size)
    }
}
