package com.ruralhealthai.app.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ruralhealthai.app.data.model.HospitalInfo
import com.ruralhealthai.app.ui.theme.*

/**
 * Card displaying a list of nearby hospitals with distance and contact info.
 */
@Composable
fun HospitalListCard(
    hospitals: List<HospitalInfo>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardDark
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = Primary.copy(alpha = 0.15f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalHospital,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Nearby Hospitals",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                    Text(
                        text = "${hospitals.size} found near you",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextTertiary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (hospitals.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = SurfaceVariant.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hospitals found nearby.\nTry enabling location services.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextTertiary
                    )
                }
            } else {
                hospitals.forEachIndexed { index, hospital ->
                    HospitalItem(hospital = hospital)
                    if (index < hospitals.lastIndex) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Divider(
                            color = SurfaceVariant.copy(alpha = 0.5f),
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun HospitalItem(hospital: HospitalInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = SurfaceVariant.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(14.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Hospital icon
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = Secondary.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.LocalHospital,
                contentDescription = null,
                tint = Secondary,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = hospital.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Distance
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DirectionsWalk,
                    contentDescription = null,
                    tint = AccentBlue,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "${hospital.distanceKm} km away",
                    style = MaterialTheme.typography.bodySmall,
                    color = AccentBlue,
                    fontWeight = FontWeight.Medium
                )
            }

            // Address
            hospital.address?.let { addr ->
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = TextTertiary,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = addr,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextTertiary
                    )
                }
            }

            // Phone
            hospital.phone?.let { phone ->
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        tint = SuccessGreen,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = phone,
                        style = MaterialTheme.typography.bodySmall,
                        color = SuccessGreen
                    )
                }
            }
        }
    }
}
