package com.example.ryanair.repository

import com.example.ryanair.db.Filters
import com.example.ryanair.db.FiltersDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FiltersRepository @Inject constructor(private val filtersDao: FiltersDao) {

    val filters: Flow<Filters> = filtersDao.get()

    fun update(filters: Filters) = filtersDao.update(filters)
}