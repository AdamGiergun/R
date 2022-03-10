package com.example.ryanair.repository

import com.example.ryanair.db.Station

interface StationsRepository {
    val stations: List<Station>?
    val error: Boolean
    val errorText: String?

    suspend fun refresh()
}