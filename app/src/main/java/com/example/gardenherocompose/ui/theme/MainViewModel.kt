package com.example.gardenherocompose.ui.theme

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gardenherocompose.model.Plant
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class MainViewModel{

    //var plants : MutableLiveData<List<Plant>> = MutableLiveData<List<Plant>>()

    private lateinit var firestore: FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    fun save(plant: Plant) {
        firestore.collection("")
    }
}