package com.ruralhealthai.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ruralhealthai.app.ui.theme.Primary
import com.ruralhealthai.app.ui.theme.PrimaryContainer
import com.ruralhealthai.app.ui.theme.SurfaceVariant
import com.ruralhealthai.app.ui.theme.TextPrimary
import com.ruralhealthai.app.ui.theme.TextSecondary

/**
 * Supported languages with their codes and display names.
 */
data class LanguageOption(
    val code: String,
    val displayName: String,
    val nativeName: String
)

val supportedLanguages = listOf(
    LanguageOption("en", "English", "English"),
    LanguageOption("hi", "Hindi", "हिन्दी"),
    LanguageOption("ta", "Tamil", "தமிழ்"),
    LanguageOption("te", "Telugu", "తెలుగు")
)

/**
 * Horizontal chip selector for choosing the input/output language.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LanguageSelector(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        supportedLanguages.forEach { lang ->
            val isSelected = selectedLanguage == lang.code
            val containerColor by animateColorAsState(
                targetValue = if (isSelected) PrimaryContainer else SurfaceVariant,
                label = "chipColor"
            )

            FilterChip(
                selected = isSelected,
                onClick = { onLanguageSelected(lang.code) },
                label = {
                    Text(
                        text = "${lang.nativeName} (${lang.displayName})",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isSelected) Primary else TextSecondary
                    )
                },
                shape = RoundedCornerShape(20.dp),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = SurfaceVariant,
                    selectedContainerColor = PrimaryContainer,
                    labelColor = TextSecondary,
                    selectedLabelColor = TextPrimary
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = SurfaceVariant,
                    selectedBorderColor = Primary,
                    enabled = true,
                    selected = isSelected
                ),
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
    }
}
