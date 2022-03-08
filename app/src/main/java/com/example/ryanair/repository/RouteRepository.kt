package com.example.ryanair.repository

import com.example.ryanair.db.Route
import com.example.ryanair.network.RyanairApi
import com.example.ryanair.network.asDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RouteRepository {

    var route: Route? = null

    suspend fun refresh(): String? {
        var message: String?
        withContext(Dispatchers.IO) {
            try {
                route = RyanairApi.retrofitRouteApiService.getRoute(
                    "2021-12-30","WRO", "DUB",1,0,0,0,"AGREED"
                ).asDbModel()
                message = null
            } catch (e: Exception) {
                message = e.localizedMessage
            }
        }
        return message
    }

}