package com.example.ryanair.repository

import com.example.ryanair.db.Filters
import com.example.ryanair.db.Route

interface RouteRepository {
    val route: Route?
    val error: Boolean
    val errorInfoId: Int
    val errorText: String

    suspend fun refresh(newFilters: Filters?)
}