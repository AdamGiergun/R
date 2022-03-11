package com.example.ryanair.repository

import com.example.ryanair.util.MockResponseFileReader
import com.example.ryanair.db.Station
import com.example.ryanair.network.StationsContainer
import com.example.ryanair.network.asDbModel
import com.squareup.moshi.Moshi

class MockStationsRepositoryImpl : StationsRepository {

    override lateinit var stations: List<Station>
        private set

    override var error = false
        private set

    override var errorText: String? = null
        private set

    lateinit var reader: MockResponseFileReader

    override suspend fun refresh() {
        try {
            val moshi: Moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(StationsContainer::class.java)
            stations = adapter.fromJson(reader.content)?.asDbModel() ?: emptyList()
            errorText = null
            error = false
        } catch (e: Exception) {
            errorText = e.localizedMessage
            error = true
        }
    }
}