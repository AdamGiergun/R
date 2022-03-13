package com.example.ryanair.repository

import com.example.ryanair.db.Filters
import kotlinx.coroutines.flow.Flow

interface FiltersRepository {

    val filters: Flow<Filters?>

    suspend fun update(newFilters: Filters)
}