package com.example.ryanair.repository

import com.example.ryanair.db.Station
import com.example.ryanair.network.RyanairApi
import com.example.ryanair.network.asDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StationsRepository {

    lateinit var stations: List<Station>

    suspend fun refresh(): String? {
        var message: String?
        withContext(Dispatchers.IO) {
            try {
                stations = RyanairApi.retrofitStationsApiService.getAirports().asDbModel()
                message = null
            } catch (e: Exception) {
                stations = emptyList()
                message = e.localizedMessage
            }
        }
        return message
    }
}