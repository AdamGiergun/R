package com.example.ryanair.repository

import com.example.ryanair.db.Filters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StubFiltersRepositoryImpl : FiltersRepository {

    override val filters: Flow<Filters?> = flow { Filters(
        0,
        "2021-12-30",
        "DUB",
        "STN",
        1,
        0,
        0,
        0
    ) }

    override suspend fun update(newFilters: Filters) {}
}