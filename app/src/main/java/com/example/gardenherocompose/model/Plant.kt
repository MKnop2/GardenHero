package com.example.gardenherocompose.model

import android.graphics.Picture
import androidx.compose.ui.graphics.painter.Painter
import com.example.gardenherocompose.R
import com.google.gson.annotations.SerializedName
import java.util.*

data class Plant(@SerializedName("name")
    var id: String = "0",
                 var picture: Int = R.drawable.pic_mint,
                 var name: String = "",
                 var species: String = "",
                 var minWaterLevel: Int = 0,
                 var maxWaterLevel: Int = 0,
                 var currentWaterLevel: Int = 0,
                 var addedDate: Date = Date(),
                 var sensorName: String = "",
                 var valve: Int = 0
)
