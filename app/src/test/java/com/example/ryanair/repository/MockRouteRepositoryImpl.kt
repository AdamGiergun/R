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
    override var errorInfoId: Int = 0
        private set
    override var errorText: String = ""
        private set

    override suspend fun refresh(newFilters: Filters?) {}

    fun initRoute(reader: MockResponseFileReader) {
        try {
            val moshi: Moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(RouteJson::class.java)
            val routeJson = adapter.fromJson(reader.content)
            route = routeJson?.asDbModel()
        } catch (e: Exception) {
            errorText = e.localizedMessage ?: "Unknown error"
            error = true
        }
    }
}