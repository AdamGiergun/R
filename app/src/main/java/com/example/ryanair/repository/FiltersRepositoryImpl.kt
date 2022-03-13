package com.example.ryanair.repository

import com.example.ryanair.db.Filters
import com.example.ryanair.db.FiltersDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FiltersRepositoryImpl @Inject constructor(private val filtersDao: FiltersDao): FiltersRepository {

    override val filters: Flow<Filters?> = filtersDao.get()

    override suspend fun update(newFilters: Filters) = filtersDao.update(newFilters)
}