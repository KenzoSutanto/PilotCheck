package com.kenzosutanto.pilotcheck

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CloudTable(clouds: List<Cloud>) {
    if (clouds.isEmpty()) {
        Text(
            text = "No Clouds Reported",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Cloud Layers", style = MaterialTheme.typography.titleMedium)

            Divider(modifier = Modifier.padding(vertical = 4.dp))

            clouds.forEach { cloud ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${cloud.base_feet_agl} ft",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = cloud.text,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
