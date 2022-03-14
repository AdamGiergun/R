package com.example.ryanair.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.ryanair.db.Filters

class DummyFiltersRepositoryImpl : FiltersRepository {

    override val filters: LiveData<Filters?> = liveData { emit(null) }

    override suspend fun update(newFilters: Filters) {}
}