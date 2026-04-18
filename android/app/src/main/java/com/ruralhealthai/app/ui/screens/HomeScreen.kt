package com.ruralhealthai.app.ui.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ruralhealthai.app.ui.components.HospitalListCard
import com.ruralhealthai.app.ui.components.InputForm
import com.ruralhealthai.app.ui.components.TriageResultCard
import com.ruralhealthai.app.ui.theme.*
import com.ruralhealthai.app.viewmodel.TriageViewModel

/**
 * Main home screen with input form, triage results, and hospital list.
 */
@Composable
fun HomeScreen(
    viewModel: TriageViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Permission launchers
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.values.all { it }
        if (granted) {
            viewModel.fetchNearbyHospitals()
        }
    }

    val micPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            viewModel.toggleVoiceInput()
        }
    }

    // Show error snackbar
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.clearError()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Gradient background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            GradientStart.copy(alpha = 0.3f),
                            GradientMiddle.copy(alpha = 0.2f),
                            GradientEnd
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
                .padding(top = 48.dp, bottom = 32.dp)
        ) {
            // ===== Header =====
            AppHeader()

            Spacer(modifier = Modifier.height(20.dp))

            // ===== Medical Disclaimer =====
            DisclaimerBanner()

            Spacer(modifier = Modifier.height(20.dp))

            // ===== Input Form Card =====
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = CardDark),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Symptom Assessment",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    InputForm(
                        age = uiState.age,
                        onAgeChange = viewModel::updateAge,
                        gender = uiState.gender,
                        onGenderChange = viewModel::updateGender,
                        symptoms = uiState.symptoms,
                        onSymptomsChange = viewModel::updateSymptoms,
                        vitals = uiState.vitals,
                        onVitalsChange = viewModel::updateVitals,
                        selectedLanguage = uiState.selectedLanguage,
                        onLanguageChange = viewModel::updateLanguage,
                        isListening = uiState.isListening,
                        onVoiceClick = {
                            micPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Submit Button
                    Button(
                        onClick = {
                            viewModel.submitTriage()
                            // Request location permission for hospital search
                            locationPermissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        },
                        enabled = !uiState.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary,
                            disabledContainerColor = Primary.copy(alpha = 0.4f)
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 6.dp,
                            pressedElevation = 2.dp
                        )
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Analyzing...",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.HealthAndSafety,
                                contentDescription = null,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Analyze Symptoms",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // ===== Results Section =====
            AnimatedVisibility(
                visible = uiState.showResults && uiState.triageResult != null,
                enter = fadeIn(tween(500)) + slideInVertically(tween(500))
            ) {
                Column {
                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "📊 Assessment Results",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimary
                        )
                        IconButton(onClick = { viewModel.resetForm() }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "New assessment",
                                tint = Primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    uiState.triageResult?.let { result ->
                        TriageResultCard(result = result)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Hospital List
                    if (uiState.isLoadingHospitals) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = CardDark)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    CircularProgressIndicator(
                                        color = Primary,
                                        modifier = Modifier.size(32.dp),
                                        strokeWidth = 3.dp
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "Finding nearby hospitals...",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = TextSecondary
                                    )
                                }
                            }
                        }
                    } else {
                        HospitalListCard(hospitals = uiState.hospitals)
                    }
                }
            }

            // Loading overlay
            if (uiState.isLoading && !uiState.showResults) {
                Spacer(modifier = Modifier.height(40.dp))
                LoadingSection()
            }
        }

        // Snackbar for errors
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) { data ->
            Snackbar(
                snackbarData = data,
                containerColor = ErrorRed,
                contentColor = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}

@Composable
private fun AppHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // App icon
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Primary, Secondary)
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.HealthAndSafety,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(
                text = "Rural Health AI",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = "Multilingual Symptom Triage Assistant",
                style = MaterialTheme.typography.bodySmall,
                color = Primary
            )
        }
    }
}

@Composable
private fun DisclaimerBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = TriageClinic.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        Text(
            text = "⚕ This app provides AI-generated guidance only. " +
                    "It is NOT a substitute for professional medical advice. " +
                    "Always consult a qualified doctor.",
            style = MaterialTheme.typography.bodySmall,
            color = TriageClinic,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun LoadingSection() {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = PrimaryContainer,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.HealthAndSafety,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier
                    .size(40.dp)
                    .rotate(rotation)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Analyzing your symptoms...",
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Our AI is reviewing your information",
            style = MaterialTheme.typography.bodyMedium,
            color = TextTertiary
        )
    }
}
