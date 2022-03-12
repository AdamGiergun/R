package com.example.ryanair.repository

import com.example.ryanair.db.Filters
import com.example.ryanair.db.Route
import com.example.ryanair.network.RyanairApi
import com.example.ryanair.network.asDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RouteRepositoryImpl @Inject constructor(): RouteRepository {

    override var route: Route? = null
        private set

    override var errorText: String? = null
        private set

    override var error = false
        private set

    override suspend fun refresh(filters: Filters) {
        withContext(Dispatchers.IO) {
            try {
                filters.run {
                    route = RyanairApi.retrofitRouteApiService.getRoute(
                        dateOut,
                        origin,
                        destination,
                        adult,
                        child,
                        teen,
                        infant,
                        termsOfUse,
                        if (roundtrip) "TRUE" else "FALSE"
                    ).asDbModel()
                }
                errorText = null
                error = false
            } catch (e: Exception) {
                errorText = e.localizedMessage
                error = true
            }
        }
    }
}