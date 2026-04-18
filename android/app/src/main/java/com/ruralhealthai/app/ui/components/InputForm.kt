package com.ruralhealthai.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ruralhealthai.app.ui.theme.Primary
import com.ruralhealthai.app.ui.theme.SurfaceVariant
import com.ruralhealthai.app.ui.theme.TextPrimary
import com.ruralhealthai.app.ui.theme.TextSecondary
import com.ruralhealthai.app.ui.theme.TextTertiary

/**
 * Main input form with Age, Gender, Symptoms (with voice), Vitals, and Language selector.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputForm(
    age: String,
    onAgeChange: (String) -> Unit,
    gender: String,
    onGenderChange: (String) -> Unit,
    symptoms: String,
    onSymptomsChange: (String) -> Unit,
    vitals: String,
    onVitalsChange: (String) -> Unit,
    selectedLanguage: String,
    onLanguageChange: (String) -> Unit,
    isListening: Boolean,
    onVoiceClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Primary,
        unfocusedBorderColor = SurfaceVariant,
        focusedLabelColor = Primary,
        unfocusedLabelColor = TextTertiary,
        cursorColor = Primary,
        focusedTextColor = TextPrimary,
        unfocusedTextColor = TextPrimary,
        focusedContainerColor = SurfaceVariant.copy(alpha = 0.3f),
        unfocusedContainerColor = SurfaceVariant.copy(alpha = 0.2f),
    )
    val fieldShape = RoundedCornerShape(14.dp)

    Column(modifier = modifier.fillMaxWidth()) {
        // Language selector
        Text(
            text = "🌐 Select Language",
            style = MaterialTheme.typography.titleSmall,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LanguageSelector(
            selectedLanguage = selectedLanguage,
            onLanguageSelected = onLanguageChange
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Age & Gender row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            // Age field
            OutlinedTextField(
                value = age,
                onValueChange = { newValue ->
                    if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                        onAgeChange(newValue)
                    }
                },
                label = { Text("Age") },
                placeholder = { Text("e.g. 35") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint = Primary
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = fieldShape,
                colors = textFieldColors,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Gender dropdown
            var genderExpanded by remember { mutableStateOf(false) }
            val genderOptions = listOf("Male", "Female", "Other")

            ExposedDropdownMenuBox(
                expanded = genderExpanded,
                onExpandedChange = { genderExpanded = it },
                modifier = Modifier.weight(1.5f)
            ) {
                OutlinedTextField(
                    value = gender,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Gender") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = genderExpanded)
                    },
                    shape = fieldShape,
                    colors = textFieldColors,
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = genderExpanded,
                    onDismissRequest = { genderExpanded = false }
                ) {
                    genderOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                onGenderChange(option)
                                genderExpanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Symptoms field with voice button
        Text(
            text = "📋 Describe Your Symptoms",
            style = MaterialTheme.typography.titleSmall,
            color = TextSecondary,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            OutlinedTextField(
                value = symptoms,
                onValueChange = onSymptomsChange,
                label = { Text("Symptoms") },
                placeholder = {
                    Text(
                        "e.g. I have headache and fever since 2 days...",
                        color = TextTertiary
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = null,
                        tint = Primary
                    )
                },
                minLines = 3,
                maxLines = 5,
                shape = fieldShape,
                colors = textFieldColors,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            VoiceInputButton(
                isListening = isListening,
                onClick = onVoiceClick,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Vitals field (optional)
        OutlinedTextField(
            value = vitals,
            onValueChange = onVitalsChange,
            label = { Text("Vitals (Optional)") },
            placeholder = {
                Text(
                    "e.g. BP: 120/80, Temp: 101°F, Pulse: 88",
                    color = TextTertiary
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Default.MonitorHeart,
                    contentDescription = null,
                    tint = Primary
                )
            },
            singleLine = true,
            shape = fieldShape,
            colors = textFieldColors,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
