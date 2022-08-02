package com.example.gardenherocompose.repository

import android.util.Log
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

        firestore.collection("plants")
            .addSnapshotListener { getResult, e ->
                val result = getResult?.documents

                val output = ArrayList<Plant>()

                result?.forEach {
                    Log.d("FB",  it.toString())
                    val tempPlant = it.toObject(Plant::class.java)
                    output.add(tempPlant!!)
                }

                Log.d("Firestore", result?.size.toString())

                liveData.value = output
            }

        return liveData
    }
}