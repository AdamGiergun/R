package com.example.ryanair.repository

import androidx.lifecycle.LiveData
import com.example.ryanair.db.Filters
import com.example.ryanair.db.FiltersDao
import javax.inject.Inject

class FiltersRepositoryImpl @Inject constructor(private val filtersDao: FiltersDao): FiltersRepository {

    override val filters: LiveData<Filters?> = filtersDao.get()

    override suspend fun update(newFilters: Filters) = filtersDao.update(newFilters)
}