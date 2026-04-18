package com.ruralhealthai.app.data.repository

import com.ruralhealthai.app.data.model.HospitalRequest
import com.ruralhealthai.app.data.model.HospitalResponse
import com.ruralhealthai.app.data.model.TriageRequest
import com.ruralhealthai.app.data.model.TriageResponse
import com.ruralhealthai.app.data.remote.RetrofitClient

/**
 * Repository that wraps API calls with error handling.
 * Acts as the single source of truth for the data layer.
 */
class HealthRepository {

    private val api = RetrofitClient.apiService

    /**
     * Submit patient data for AI triage analysis.
     *
     * @return Result wrapping either a TriageResponse or an exception
     */
    suspend fun submitTriage(
        age: Int,
        gender: String,
        symptoms: String,
        vitals: String?,
        language: String
    ): Result<TriageResponse> {
        return try {
            val request = TriageRequest(
                age = age,
                gender = gender,
                symptoms = symptoms,
                vitals = vitals?.takeIf { it.isNotBlank() },
                language = language
            )
            val response = api.submitTriage(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Find hospitals near the given coordinates.
     *
     * @return Result wrapping either a HospitalResponse or an exception
     */
    suspend fun findHospitals(
        latitude: Double,
        longitude: Double
    ): Result<HospitalResponse> {
        return try {
            val request = HospitalRequest(
                latitude = latitude,
                longitude = longitude
            )
            val response = api.findHospitals(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
