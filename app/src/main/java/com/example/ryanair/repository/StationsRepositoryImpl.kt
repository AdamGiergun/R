package com.example.ryanair.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ryanair.db.Station
import com.example.ryanair.network.RyanairApi
import com.example.ryanair.network.asDbModel
import javax.inject.Inject

class StationsRepositoryImpl @Inject constructor(): StationsRepository {

    private val _stations= MutableLiveData<List<Station>?>()
    override val stations: LiveData<List<Station>?>
        get() = _stations

    private val _error= MutableLiveData<Boolean?>()
    override val error: LiveData<Boolean?>
        get() = _error

    private val _errorText = MutableLiveData<String?>()
    override val errorText: LiveData<String?>
        get() = _errorText

    override suspend fun refresh() {
        try {
            _stations.value = RyanairApi.retrofitStationsApiService.getStationsContainer().asDbModel()
            _errorText.value = null
            _error.value = false
        } catch (e: Exception) {
            _stations.value = null
            _error.value = true
            _errorText.value = e.localizedMessage
        }
    }
}