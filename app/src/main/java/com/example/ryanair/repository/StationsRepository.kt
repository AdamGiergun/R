package com.example.ryanair.repository

import com.example.ryanair.db.Station
import com.example.ryanair.network.RyanairApi
import com.example.ryanair.network.asDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StationsRepository {

    var stations: List<Station>? = null
        private set

    var errorText: String? = null
        private set

    var error = false
        private set

    suspend fun refresh() = withContext(Dispatchers.IO) {
        try {
            stations = RyanairApi.retrofitStationsApiService.getAirports().asDbModel()
            errorText = null
            error = false
        } catch (e: Exception) {
            stations = null
            errorText = e.localizedMessage
            error = true
        }
    }
}