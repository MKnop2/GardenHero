package com.example.gardenherocompose.model

import android.graphics.Picture
import androidx.compose.ui.graphics.painter.Painter
import com.google.gson.annotations.SerializedName
import java.util.*

data class Plant(@SerializedName("name")
    var id: String = "0",
    var picture: Int = 0,
    var name: String = "",
    var species: String = "",
    val minWaterLevel: Int = 0,
    val maxWaterLevel: Int = 0,
    var currentWaterLevel: Int = 0,
    var lastPour: Date = Date(),
    var nextPour: Date = Date(),
    var sensorName: String = "",
    var valve: Int = 0
)
