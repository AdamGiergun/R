package com.example.ryanair.repository

import com.example.ryanair.db.Station
import com.example.ryanair.network.RyanairApi
import com.example.ryanair.network.asDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StationsRepositoryImpl @Inject constructor(): StationsRepository {

    override var stations: List<Station>? = null
        private set

    override var errorText: String? = null
        private set

    override var error = false
        private set

    override suspend fun refresh() = withContext(Dispatchers.IO) {
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