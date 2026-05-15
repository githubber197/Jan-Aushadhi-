package com.janaushadhi.finder.data.model

import com.google.gson.annotations.SerializedName

data class Store(
    val id: Int,
    val name: String,
    val address: String,
    val city: String,
    val pincode: String?,
    val phone: String?,
    val lat: Double?,
    val lng: Double?,
    val is_open: Int,
    @SerializedName("distance_km")
    val distanceKm: Double? = null
) {
    val isOpen: Boolean get() = is_open == 1
    val statusLabel: String get() = if (isOpen) "Open" else "Closed"
    val fullAddress: String get() = "$address, $city${if (!pincode.isNullOrBlank()) " - $pincode" else ""}"
    val distanceLabel: String get() = when {
        distanceKm == null -> ""
        distanceKm < 1.0   -> "${(distanceKm * 1000).toInt()} m away"
        else                -> "${"%.1f".format(distanceKm)} km away"
    }
}
