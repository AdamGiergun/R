package com.example.ryanair

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ryanair.repository.StationsRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val stationsRepository = StationsRepository()

    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text

    init {
        viewModelScope.launch {
            stationsRepository.refreshStations()
            _text.value = stationsRepository.run {
                var result = ""
                this.stations.forEach { station ->
                    result += "${station.code}\n"
                }
                result
            }
        }
    }
}