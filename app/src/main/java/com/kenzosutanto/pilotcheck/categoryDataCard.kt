package com.kenzosutanto.pilotcheck

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun CategoryDataCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    val textColor = when (value) {
        "VFR" -> Color(0xFF4CAF50) // Green
        "MVFR" -> Color(0xFF2196F3) // Blue
        "IFR" -> Color(0xFFFF5722) // Bright Red
        "LIFR" -> Color(0xFFB71C1C) // Dark Red
        else -> MaterialTheme.colorScheme.onSurface // Default color
    }

    Card(
        modifier = modifier
            .padding(4.dp)
            .height(120.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor, // Dynamic text color
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
