package com.example.ryanair.repository

import com.example.ryanair.db.Filters
import com.example.ryanair.db.Route

interface RouteRepository {
    val route: Route?
    val error: Boolean
    val errorText: String?

    suspend fun refresh(filters: Filters)
}