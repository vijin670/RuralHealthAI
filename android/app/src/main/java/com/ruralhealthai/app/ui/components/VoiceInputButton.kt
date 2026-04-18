package com.ruralhealthai.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ruralhealthai.app.ui.theme.ErrorRed
import com.ruralhealthai.app.ui.theme.Primary
import com.ruralhealthai.app.ui.theme.PrimaryContainer
import com.ruralhealthai.app.ui.theme.TextPrimary

/**
 * Animated microphone button for voice input.
 * Pulses red when actively listening.
 */
@Composable
fun VoiceInputButton(
    isListening: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isListening) ErrorRed else PrimaryContainer,
        label = "micBg"
    )

    val iconColor by animateColorAsState(
        targetValue = if (isListening) Color.White else Primary,
        label = "micIcon"
    )

    // Pulsing animation when listening
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isListening) 1.15f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        // Outer glow ring when listening
        if (isListening) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .scale(scale)
                    .background(
                        color = ErrorRed.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
            )
        }

        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = backgroundColor,
                    shape = CircleShape
                ),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent,
                contentColor = iconColor
            )
        ) {
            Icon(
                imageVector = if (isListening) Icons.Default.Mic else Icons.Default.MicOff,
                contentDescription = if (isListening) "Stop listening" else "Start voice input",
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
