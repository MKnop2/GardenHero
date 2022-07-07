package com.example.gardenherocompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gardenherocompose.model.Plant
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import java.util.*


@Composable
fun CustomItem(plant: Plant) {
    Row(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = plant.name,
            color = Color.Black,
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = plant.species,
            color = Color.Black,
            fontSize = MaterialTheme.typography.h5.fontSize,
            fontWeight = FontWeight.Normal
        )
        Text(
            text = plant.currentWaterLevel.toString(),
            color = Color.Black,
            fontSize = MaterialTheme.typography.h5.fontSize,
            fontWeight = FontWeight.Normal
        )
    }
}


@Composable
@Preview
fun CustomItemPreview() {
    CustomItem(
        plant = Plant(
            id = 0,
            picture = R.drawable.ic_launcher_background,
            name = "Björn",
            species = "Cannabis",
            minWaterLevel = 60,
            maxWaterLevel = 85,
            currentWaterLevel = 62,
            lastPour = Date(),
            nextPour = Date(),
            sensorName = "Sensor1",
            valve = 1,
        )
    )
}