package com.example.gardenherocompose.repository

import android.util.Log
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gardenherocompose.R
import com.example.gardenherocompose.model.Plant
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class PlantRepository {
    fun getDataFromFirestore(): LiveData<List<Plant>> {
        val firestore = FirebaseFirestore.getInstance()
        val liveData = MutableLiveData<List<Plant>>()

        firestore.collection("plants").get()
            .addOnSuccessListener { getResult ->
                val result = getResult.documents

                val output = ArrayList<Plant>()

                result.forEach {
                    Log.d("FB",  it.toString())
                    val tempPlant = it.toObject(Plant::class.java)
                    tempPlant!!.picture = R.drawable.pic_cannabis
                    output.add(tempPlant)
                }

                Log.d("Firestore", result.size.toString())

                liveData.value = output
            }

        return liveData
    }

    fun getAllData(): List<Plant> {
        return listOf(
            Plant(
                id = "0",
                picture = R.drawable.pic_cannabis,
                name = "Björn",
                species = "Cannabis",
                minWaterLevel = 60,
                maxWaterLevel = 85,
                currentWaterLevel = 62,
                lastPour = Date(),
                nextPour = Date(),
                sensorName = "Sensor1",
                valve = 1
            ),
            Plant(
                id = "1",
                picture = R.drawable.pic_mint,
                name = "Minze",
                species = "Mint",
                minWaterLevel = 70,
                maxWaterLevel = 90,
                currentWaterLevel = 85,
                lastPour = Date(),
                nextPour = Date(),
                sensorName = "Sensor2",
                valve = 2
            ),
            Plant(
                id = "2",
                picture = R.drawable.pic_orchid,
                name = "Lilly",
                species = "Orchid",
                minWaterLevel = 30,
                maxWaterLevel = 40,
                currentWaterLevel = 34,
                lastPour = Date(),
                nextPour = Date(),
                sensorName = "Sensor3",
                valve = 3
            ),
            Plant(
                id = "3",
                picture = R.drawable.pic_triandra,
                name = "Areca1",
                species = "Triandra",
                minWaterLevel = 55,
                maxWaterLevel = 95,
                currentWaterLevel = 60,
                lastPour = Date(),
                nextPour = Date(),
                sensorName = "Sensor4",
                valve = 4
            ),
            Plant(
                id = "4",
                picture = R.drawable.pic_triandra,
                name = "Areca2",
                species = "Triandra",
                minWaterLevel = 55,
                maxWaterLevel = 95,
                currentWaterLevel = 90,
                lastPour = Date(),
                nextPour = Date(),
                sensorName = "Sensor5",
                valve = 5
            ),
            Plant(
                id = "5",
                picture = R.drawable.pic_triandra,
                name = "Areca3",
                species = "Triandra",
                minWaterLevel = 55,
                maxWaterLevel = 95,
                currentWaterLevel = 80,
                lastPour = Date(),
                nextPour = Date(),
                sensorName = "Sensor6",
                valve = 6
            ),
            Plant(
                id = "6",
                picture = R.drawable.pic_dionaea,
                name = "Killer",
                species = "Dionaea muscipula",
                minWaterLevel = 35,
                maxWaterLevel = 55,
                currentWaterLevel = 37,
                lastPour = Date(),
                nextPour = Date(),
                sensorName = "Sensor6",
                valve = 7
            ),
            Plant(
                id = "7",
                picture = R.drawable.pic_triandra,
                name = "Areca5",
                species = "Triandra",
                minWaterLevel = 55,
                maxWaterLevel = 95,
                currentWaterLevel = 80,
                lastPour = Date(),
                nextPour = Date(),
                sensorName = "Sensor6",
                valve = 8
            ),
            Plant(
                id = "8",
                picture = R.drawable.pic_triandra,
                name = "Areca6",
                species = "Triandra",
                minWaterLevel = 55,
                maxWaterLevel = 95,
                currentWaterLevel = 80,
                lastPour = Date(),
                nextPour = Date(),
                sensorName = "Sensor6",
                valve = 9
            ),
            Plant(
                id = "9",
                picture = R.drawable.pic_triandra,
                name = "Areca7",
                species = "Triandra",
                minWaterLevel = 55,
                maxWaterLevel = 95,
                currentWaterLevel = 80,
                lastPour = Date(),
                nextPour = Date(),
                sensorName = "Sensor6",
                valve = 10
            ),
        )
    }
}