package com.ruralhealthai.app.viewmodel

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.ruralhealthai.app.data.model.HospitalInfo
import com.ruralhealthai.app.data.model.TriageResponse
import com.ruralhealthai.app.data.repository.HealthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

private const val TAG = "TriageViewModel"

/**
 * UI state for the Home screen.
 */
data class TriageUiState(
    // Input fields
    val age: String = "",
    val gender: String = "Male",
    val symptoms: String = "",
    val vitals: String = "",
    val selectedLanguage: String = "en",

    // Processing state
    val isLoading: Boolean = false,
    val isListening: Boolean = false,
    val isLoadingHospitals: Boolean = false,

    // Results
    val triageResult: TriageResponse? = null,
    val hospitals: List<HospitalInfo> = emptyList(),

    // Errors
    val errorMessage: String? = null,
    val showResults: Boolean = false
)

/**
 * ViewModel managing form state, API calls, voice recognition, and location.
 */
class TriageViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = HealthRepository()
    private val _uiState = MutableStateFlow(TriageUiState())
    val uiState: StateFlow<TriageUiState> = _uiState.asStateFlow()

    private var speechRecognizer: SpeechRecognizer? = null
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    // Language code to Locale mapping for speech recognition
    private val languageLocaleMap = mapOf(
        "en" to Locale.ENGLISH,
        "hi" to Locale("hi", "IN"),
        "ta" to Locale("ta", "IN"),
        "te" to Locale("te", "IN")
    )

    // ===== Input field updates =====

    fun updateAge(value: String) {
        _uiState.update { it.copy(age = value) }
    }

    fun updateGender(value: String) {
        _uiState.update { it.copy(gender = value) }
    }

    fun updateSymptoms(value: String) {
        _uiState.update { it.copy(symptoms = value) }
    }

    fun updateVitals(value: String) {
        _uiState.update { it.copy(vitals = value) }
    }

    fun updateLanguage(value: String) {
        _uiState.update { it.copy(selectedLanguage = value) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    // ===== Voice Recognition =====

    fun toggleVoiceInput() {
        if (_uiState.value.isListening) {
            stopListening()
        } else {
            startListening()
        }
    }

    private fun startListening() {
        val context = getApplication<Application>()
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            _uiState.update {
                it.copy(errorMessage = "Voice recognition is not available on this device")
            }
            return
        }

        speechRecognizer?.destroy()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

        val locale = languageLocaleMap[_uiState.value.selectedLanguage] ?: Locale.ENGLISH

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale.toLanguageTag())
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }

        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                _uiState.update { it.copy(isListening = true) }
            }

            override fun onBeginningOfSpeech() {}

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {
                _uiState.update { it.copy(isListening = false) }
            }

            override fun onError(error: Int) {
                Log.e(TAG, "Speech recognition error: $error")
                _uiState.update {
                    it.copy(
                        isListening = false,
                        errorMessage = when (error) {
                            SpeechRecognizer.ERROR_NO_MATCH -> "No speech detected. Please try again."
                            SpeechRecognizer.ERROR_NETWORK -> "Network error. Check your connection."
                            SpeechRecognizer.ERROR_AUDIO -> "Audio recording error."
                            else -> "Voice input error. Please try again."
                        }
                    )
                }
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val spokenText = matches?.firstOrNull() ?: ""
                if (spokenText.isNotBlank()) {
                    _uiState.update { state ->
                        val currentSymptoms = state.symptoms
                        val newSymptoms = if (currentSymptoms.isBlank()) {
                            spokenText
                        } else {
                            "$currentSymptoms. $spokenText"
                        }
                        state.copy(symptoms = newSymptoms, isListening = false)
                    }
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {
                val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val partial = matches?.firstOrNull() ?: ""
                if (partial.isNotBlank()) {
                    // Show partial results in real-time
                    Log.d(TAG, "Partial: $partial")
                }
            }

            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        speechRecognizer?.startListening(intent)
    }

    private fun stopListening() {
        speechRecognizer?.stopListening()
        _uiState.update { it.copy(isListening = false) }
    }

    // ===== Triage Submission =====

    fun submitTriage() {
        val state = _uiState.value

        // Validation
        if (state.age.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Please enter your age") }
            return
        }
        if (state.symptoms.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Please describe your symptoms") }
            return
        }

        val ageInt = state.age.toIntOrNull()
        if (ageInt == null || ageInt < 0 || ageInt > 120) {
            _uiState.update { it.copy(errorMessage = "Please enter a valid age (0-120)") }
            return
        }

        _uiState.update {
            it.copy(isLoading = true, errorMessage = null, showResults = false)
        }

        viewModelScope.launch {
            val result = repository.submitTriage(
                age = ageInt,
                gender = state.gender,
                symptoms = state.symptoms,
                vitals = state.vitals,
                language = state.selectedLanguage
            )

            result.onSuccess { response ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        triageResult = response,
                        showResults = true
                    )
                }
                // Auto-fetch hospitals after triage
                fetchNearbyHospitals()
            }.onFailure { error ->
                Log.e(TAG, "Triage failed", error)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to analyze symptoms. Please check your connection and try again."
                    )
                }
            }
        }
    }

    // ===== Hospital Search =====

    @Suppress("MissingPermission")
    fun fetchNearbyHospitals() {
        _uiState.update { it.copy(isLoadingHospitals = true) }

        try {
            val cancellationToken = CancellationTokenSource()
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                cancellationToken.token
            ).addOnSuccessListener { location ->
                if (location != null) {
                    viewModelScope.launch {
                        val result = repository.findHospitals(
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                        result.onSuccess { response ->
                            _uiState.update {
                                it.copy(
                                    hospitals = response.hospitals,
                                    isLoadingHospitals = false
                                )
                            }
                        }.onFailure { error ->
                            Log.e(TAG, "Hospital search failed", error)
                            _uiState.update {
                                it.copy(isLoadingHospitals = false)
                            }
                        }
                    }
                } else {
                    _uiState.update {
                        it.copy(isLoadingHospitals = false)
                    }
                }
            }.addOnFailureListener { error ->
                Log.e(TAG, "Location fetch failed", error)
                _uiState.update {
                    it.copy(isLoadingHospitals = false)
                }
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "Location permission not granted", e)
            _uiState.update {
                it.copy(
                    isLoadingHospitals = false,
                    errorMessage = "Location permission needed to find nearby hospitals"
                )
            }
        }
    }

    // ===== Reset =====

    fun resetForm() {
        speechRecognizer?.destroy()
        _uiState.value = TriageUiState()
    }

    override fun onCleared() {
        super.onCleared()
        speechRecognizer?.destroy()
    }
}
