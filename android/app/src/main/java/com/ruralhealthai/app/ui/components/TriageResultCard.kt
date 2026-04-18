package com.ruralhealthai.app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Healing
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ruralhealthai.app.data.model.TriageResponse
import com.ruralhealthai.app.ui.theme.*

/**
 * Get triage level display properties (color, background, icon, label).
 */
data class TriageLevelStyle(
    val color: Color,
    val backgroundColor: Color,
    val icon: ImageVector,
    val label: String,
    val description: String
)

fun getTriageLevelStyle(level: String): TriageLevelStyle {
    return when (level.lowercase()) {
        "self-care" -> TriageLevelStyle(
            color = TriageSelfCare,
            backgroundColor = TriageSelfCareBg,
            icon = Icons.Default.CheckCircle,
            label = "SELF-CARE",
            description = "Manageable at home with basic care"
        )
        "clinic" -> TriageLevelStyle(
            color = TriageClinic,
            backgroundColor = TriageClinicBg,
            icon = Icons.Default.MedicalServices,
            label = "CLINIC VISIT",
            description = "Visit a doctor when possible"
        )
        "hospital" -> TriageLevelStyle(
            color = TriageHospital,
            backgroundColor = TriageHospitalBg,
            icon = Icons.Default.LocalHospital,
            label = "HOSPITAL",
            description = "Requires hospital care soon"
        )
        "emergency" -> TriageLevelStyle(
            color = TriageEmergency,
            backgroundColor = TriageEmergencyBg,
            icon = Icons.Default.Error,
            label = "⚠ EMERGENCY",
            description = "Seek immediate medical help!"
        )
        else -> TriageLevelStyle(
            color = TriageClinic,
            backgroundColor = TriageClinicBg,
            icon = Icons.Default.Help,
            label = "UNKNOWN",
            description = "Please consult a healthcare professional"
        )
    }
}

/**
 * Card displaying the complete triage results with animated sections.
 */
@Composable
fun TriageResultCard(
    result: TriageResponse,
    modifier: Modifier = Modifier
) {
    val style = getTriageLevelStyle(result.triageLevel)

    // Pulsing for emergency
    val infiniteTransition = rememberInfiniteTransition(label = "emergencyPulse")
    val emergencyAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "emergencyAlpha"
    )

    val borderAlpha = if (result.triageLevel.lowercase() == "emergency") emergencyAlpha else 1f

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .border(
                width = 2.dp,
                color = style.color.copy(alpha = borderAlpha),
                shape = RoundedCornerShape(20.dp)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardDark
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Triage Level Banner
            TriageLevelBanner(style = style)

            Spacer(modifier = Modifier.height(20.dp))
            Divider(color = SurfaceVariant, thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            // Follow-up question
            result.followUpQuestion?.let { question ->
                FollowUpSection(question = question)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Probable Conditions
            if (result.conditions.isNotEmpty()) {
                SectionHeader(
                    icon = Icons.Default.Healing,
                    title = "Probable Conditions"
                )
                Spacer(modifier = Modifier.height(10.dp))

                result.conditions.forEachIndexed { index, condition ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(tween(300 * (index + 1))) +
                                slideInVertically(tween(300 * (index + 1)))
                    ) {
                        ConditionItem(
                            name = condition.condition,
                            confidence = condition.confidence,
                            description = condition.description
                        )
                    }
                    if (index < result.conditions.lastIndex) {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = SurfaceVariant, thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))
            }

            // First Aid Instructions
            if (result.firstAid.isNotEmpty()) {
                SectionHeader(
                    icon = Icons.Default.MedicalServices,
                    title = "First-Aid Steps"
                )
                Spacer(modifier = Modifier.height(10.dp))

                result.firstAid.forEachIndexed { index, step ->
                    FirstAidStep(stepNumber = index + 1, step = step)
                    if (index < result.firstAid.lastIndex) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = SurfaceVariant, thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            // Disclaimer
            Text(
                text = "⚕ ${result.disclaimer}",
                style = MaterialTheme.typography.bodySmall,
                color = TextTertiary,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun TriageLevelBanner(style: TriageLevelStyle) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        style.backgroundColor,
                        style.backgroundColor.copy(alpha = 0.5f)
                    )
                ),
                shape = RoundedCornerShape(14.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = style.color.copy(alpha = 0.2f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = style.icon,
                    contentDescription = null,
                    tint = style.color,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = style.label,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = style.color
                )
                Text(
                    text = style.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(icon: ImageVector, title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Primary,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
    }
}

@Composable
private fun ConditionItem(
    name: String,
    confidence: Float,
    description: String
) {
    val confidencePercent = (confidence * 100).toInt()
    val barColor = when {
        confidence >= 0.7f -> ConfidenceHigh
        confidence >= 0.4f -> ConfidenceMedium
        else -> ConfidenceLow
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = SurfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "$confidencePercent%",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = barColor
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        LinearProgressIndicator(
            progress = { confidence },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = barColor,
            trackColor = SurfaceVariant,
            strokeCap = StrokeCap.Round,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = TextTertiary
        )
    }
}

@Composable
private fun FirstAidStep(stepNumber: Int, step: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = SecondaryContainer.copy(alpha = 0.4f),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .background(
                    color = Secondary.copy(alpha = 0.2f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$stepNumber",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = Secondary
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = step,
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun FollowUpSection(question: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = AccentBlue.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = AccentBlue.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Icon(
                imageVector = Icons.Default.Help,
                contentDescription = null,
                tint = AccentBlue,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "Follow-up Question",
                    style = MaterialTheme.typography.labelLarge,
                    color = AccentBlue,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = question,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary
                )
            }
        }
    }
}
