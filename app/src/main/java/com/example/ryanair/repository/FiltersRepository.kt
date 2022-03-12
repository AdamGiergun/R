package com.example.ryanair.repository

import com.example.ryanair.db.Filters

interface FiltersRepository {

    fun getFilters() : Filters

    fun update(filters: Filters)
}