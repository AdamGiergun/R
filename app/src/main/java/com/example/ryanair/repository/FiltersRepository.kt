package com.example.ryanair.repository

import androidx.lifecycle.LiveData
import com.example.ryanair.db.Filters

interface FiltersRepository {

    val filters: LiveData<Filters?>

    suspend fun update(newFilters: Filters)
}