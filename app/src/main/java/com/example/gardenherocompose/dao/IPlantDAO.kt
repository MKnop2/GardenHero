package com.example.gardenherocompose.dao

import retrofit2.Call
import com.example.gardenherocompose.model.Plant
import retrofit2.http.GET

interface IPlantDAO {
    @GET
    fun getAllPlants() : Call<ArrayList<Plant>>
}