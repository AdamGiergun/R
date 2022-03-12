package com.example.ryanair.repository

import com.example.ryanair.db.Filters
import com.example.ryanair.db.FiltersDao
import javax.inject.Inject

class FiltersRepository @Inject constructor(private val filtersDao: FiltersDao) {

    fun getFilters() = filtersDao.get()

    fun update(filters: Filters) = filtersDao.update(filters)
}