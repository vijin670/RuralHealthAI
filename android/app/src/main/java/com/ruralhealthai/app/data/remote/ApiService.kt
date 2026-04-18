package com.ruralhealthai.app.data.remote

import com.ruralhealthai.app.data.model.HospitalRequest
import com.ruralhealthai.app.data.model.HospitalResponse
import com.ruralhealthai.app.data.model.TriageRequest
import com.ruralhealthai.app.data.model.TriageResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Retrofit interface defining the Rural Health AI backend API endpoints.
 */
interface ApiService {

    /**
     * Submit symptoms for AI-powered triage analysis.
     */
    @POST("/triage")
    suspend fun submitTriage(@Body request: TriageRequest): TriageResponse

    /**
     * Search for hospitals near the given coordinates.
     */
    @POST("/hospitals")
    suspend fun findHospitals(@Body request: HospitalRequest): HospitalResponse
}
