package com.kenzosutanto.pilotcheck

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CloudLayerVisualizerWithoutCanvas(
    clouds: List<Cloud>,
    modifier: Modifier = Modifier
) {
    // Ensure a minimum maxHeight of 2000 ft
    val highestCloud = clouds.maxOfOrNull { it.base_feet_agl } ?: 0
    val maxHeight = (highestCloud + 500).coerceAtLeast(2000) // Add buffer
    val step = maxHeight / 5 // Divide the scale into 5 intervals
    val cloudLayerHeight = 1000.dp // Fixed height for the visualizer

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(cloudLayerHeight)
            .clip(RoundedCornerShape(12.dp)) // Rounded corners
            .background(Color(0xFF0A84FF)) // Sky blue background
    ) {
        // Altitude Levels
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in maxHeight downTo 0 step step) {
                Text(
                    text = "$i ft",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        // Cloud Markers
        clouds.forEach { cloud ->
            // Proportional position within the height in Dp
            val cloudPositionDp = cloudLayerHeight * (1f - (cloud.base_feet_agl / maxHeight.toFloat()))

            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(y = cloudPositionDp) // Use calculated Dp position
                    .padding(horizontal = 16.dp)
                    .wrapContentSize()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "${cloud.text} ${cloud.base_feet_agl}ft", // Display cloud type and altitude
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }
        }

        // Placeholder for no clouds
        if (clouds.isEmpty()) {
            Text(
                text = "No clouds",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
