package com.example.ryanair.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ryanair.db.Route
import com.example.ryanair.db.Station
import com.example.ryanair.repository.RouteRepository
import com.example.ryanair.repository.StationsRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val stationsRepository = StationsRepository()
    private val routeRepository = RouteRepository()

    private val _stations = MutableLiveData<List<Station>>()
    val stations: LiveData<List<Station>>
        get() = _stations

    private val _route = MutableLiveData<Route>()
    val route: LiveData<Route>
        get() = _route

    private val _errorText = MutableLiveData<String>()
    val errorText: LiveData<String>
        get() = _errorText

    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text

    private val _search = MutableLiveData(false)
    val search: LiveData<Boolean>
        get() = _search
    fun search() {
        _search.value = true
//        _search.value = false
    }

    init {
        viewModelScope.launch {
            val text1 = initStations()
            val text2 = initRoute()
            _text.value = "$text1\n$text2"
        }
    }

    private suspend fun initStations(): String {
        var newText = ""
        stationsRepository.refresh()?.let { _errorText.value = it }
        stationsRepository.run {
            _stations.value = stations
            stations.forEach { station ->
                newText += "${station.code} "
            }
        }
        return newText
    }


    private suspend fun initRoute(): String {
        var newText = ""
        routeRepository.refresh()?.let { _errorText.value = it }
        newText += routeRepository.route.toString()
        return newText
    }
}