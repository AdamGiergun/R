package com.example.ryanair

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ryanair.db.Station
import com.example.ryanair.repository.StationsRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val stationsRepository = StationsRepository()

    private val _stations = MutableLiveData<List<Station>>()
    val stations: LiveData<List<Station>>
        get() = _stations

    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text

    init {
        viewModelScope.launch {
            var newText = ""
            stationsRepository.refreshStations()?.let { newText = it }
            _text.value = stationsRepository.run {
                _stations.value = stations
                stations.forEach { station ->
                    newText += "${station.code}\n"
                }
                newText
            }
        }
    }
}