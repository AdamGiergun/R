package com.example.ryanair.repository

import com.example.ryanair.db.Filters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFiltersRepositoryImpl : FiltersRepository {

    override val filters: Flow<Filters?> = flow { emit(null) }

    override suspend fun update(newFilters: Filters) {}
}