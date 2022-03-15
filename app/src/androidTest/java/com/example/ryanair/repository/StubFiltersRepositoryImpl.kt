package com.example.ryanair.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.ryanair.db.Filters
import javax.inject.Inject

class StubFiltersRepositoryImpl @Inject constructor(): FiltersRepository {

    override val filters: LiveData<Filters?> = liveData {
        emit(
            Filters(
                0,
                "2021-12-30",
                "DUB",
                "STN",
                1,
                0,
                0,
                0
            )
        )
    }

    override suspend fun update(newFilters: Filters) {}
}