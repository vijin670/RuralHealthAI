package com.ruralhealthai.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Information about a nearby hospital.
 */
data class HospitalInfo(
    @SerializedName("name")
    val name: String,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("distance_km")
    val distanceKm: Double,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("phone")
    val phone: String? = null
)

/**
 * Wrapper response for hospital search.
 */
data class HospitalResponse(
    @SerializedName("hospitals")
    val hospitals: List<HospitalInfo>,

    @SerializedName("count")
    val count: Int
)

/**
 * Request body for hospital search.
 */
data class HospitalRequest(
    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double
)
