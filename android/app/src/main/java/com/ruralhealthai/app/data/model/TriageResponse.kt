package com.ruralhealthai.app.data.model

import com.google.gson.annotations.SerializedName

/**
 * Response from the /triage endpoint.
 */
data class TriageResponse(
    @SerializedName("triage_level")
    val triageLevel: String,

    @SerializedName("conditions")
    val conditions: List<ConditionInfo>,

    @SerializedName("first_aid")
    val firstAid: List<String>,

    @SerializedName("follow_up_question")
    val followUpQuestion: String? = null,

    @SerializedName("disclaimer")
    val disclaimer: String
)

/**
 * A probable medical condition with confidence score.
 */
data class ConditionInfo(
    @SerializedName("condition")
    val condition: String,

    @SerializedName("confidence")
    val confidence: Float,

    @SerializedName("description")
    val description: String
)
