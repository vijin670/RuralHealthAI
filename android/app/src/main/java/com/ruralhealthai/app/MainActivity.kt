package com.ruralhealthai.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ruralhealthai.app.ui.screens.HomeScreen
import com.ruralhealthai.app.ui.theme.BackgroundDark
import com.ruralhealthai.app.ui.theme.RuralHealthAITheme
import com.ruralhealthai.app.viewmodel.TriageViewModel

/**
 * Main entry point for the Rural Health AI Android application.
 * Single-activity architecture with Jetpack Compose.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RuralHealthAITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackgroundDark
                ) {
                    val viewModel: TriageViewModel = viewModel()
                    HomeScreen(viewModel = viewModel)
                }
            }
        }
    }
}
