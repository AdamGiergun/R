package com.example.ryanair.repository

import androidx.lifecycle.LiveData
import com.example.ryanair.db.Station

interface StationsRepository {

    val stations: LiveData<List<Station>?>
    val error: LiveData<Boolean?>
    val errorText: LiveData<String?>

    suspend fun refresh()
}