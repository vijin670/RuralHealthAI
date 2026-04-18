package com.ruralhealthai.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Request body sent to the /triage endpoint.
 */
data class TriageRequest(
    @SerializedName("age")
    val age: Int,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("symptoms")
    val symptoms: String,

    @SerializedName("vitals")
    val vitals: String? = null,

    @SerializedName("language")
    val language: String = "en"
)
