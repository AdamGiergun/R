package com.example.ryanair.repository

import androidx.lifecycle.LiveData
import com.example.ryanair.db.Filters
import com.example.ryanair.db.Route

interface RouteRepository {

    val route: LiveData<Route?>
    val error: LiveData<Boolean?>
    val errorInfoId: LiveData<Int?>
    val errorText: LiveData<String?>

    suspend fun refresh(newFilters: Filters?)
}