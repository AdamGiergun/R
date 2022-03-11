package com.example.ryanair.repository

import com.example.ryanair.util.MockResponseFileReader
import com.example.ryanair.db.Filters
import com.example.ryanair.db.Route
import com.example.ryanair.network.RouteJson
import com.example.ryanair.network.asDbModel
import com.squareup.moshi.Moshi

class MockRouteRepositoryImpl : RouteRepository {

    override var route: Route? = null
        private set

    override var error: Boolean = false
        private set

    override var errorText: String? = null
        private set

    lateinit var reader: MockResponseFileReader

    override suspend fun refresh(filters: Filters) {
        try {
            val moshi: Moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(RouteJson::class.java)
            route = adapter.fromJson(reader.content)?.asDbModel()
            errorText = null
            error = false
        } catch (e: Exception) {
            errorText = e.localizedMessage
            error = true
        }
    }
}