package com.example.ryanair.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ryanair.db.Station
import com.example.ryanair.network.StationsContainer
import com.example.ryanair.network.asDbModel
import com.example.ryanair.util.MockResponseFileReader
import com.squareup.moshi.Moshi
import javax.inject.Inject

class MockStationsRepositoryAtImpl  @Inject constructor(): StationsRepository {

    private val _stations = MutableLiveData<List<Station>?>()
    override val stations: LiveData<List<Station>?>
        get() = _stations

    private val _error = MutableLiveData<Boolean>()
    override val error: LiveData<Boolean?>
        get() = _error

    private val _errorText = MutableLiveData<String?>()
    override val errorText: LiveData<String?>
        get() = _errorText

    override suspend fun refresh() {
        try {
            val moshi: Moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(StationsContainer::class.java)
            val reader = MockResponseFileReader("allStations.json")
            _stations.value = adapter.fromJson(reader.content)?.asDbModel() ?: emptyList()
            _errorText.value = null
            _error.value = false
        } catch (e: Exception) {
            _errorText.value = e.localizedMessage
            _error.value = true
        }
    }
}