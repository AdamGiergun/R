package com.example.ryanair.repository

import com.example.ryanair.db.Filters
import com.example.ryanair.db.FiltersDao
import javax.inject.Inject

class FiltersRepositoryImpl @Inject constructor(private val filtersDao: FiltersDao): FiltersRepository {

    override fun getFilters() = filtersDao.get()

    override fun update(filters: Filters) = filtersDao.update(filters)
}