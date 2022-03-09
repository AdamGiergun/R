package com.example.ryanair.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ryanair.db.Filters
import com.example.ryanair.db.Route
import com.example.ryanair.db.Station
import com.example.ryanair.repository.RouteRepository
import com.example.ryanair.repository.StationsRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel() {

    private val stationsRepository = StationsRepository()
    private val routeRepository = RouteRepository()

    private val _stations = MutableLiveData<List<Station>>()
    val stations: LiveData<List<Station>>
        get() = _stations

    private val _route = MutableLiveData<Route>()
    val route: LiveData<Route>
        get() = _route

    var error = false
        private set
    private val _errorText = MutableLiveData<String>()
    val errorText: LiveData<String>
        get() = _errorText

    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text

    private val _search = MutableLiveData<Boolean>()
    val search: LiveData<Boolean>
        get() = _search

    fun search() {
        _search.value = true
    }

    private val _filters = MutableLiveData(
        Filters(
            Calendar.getInstance().run {
                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                formatter.format(time)
            },
            "DUB",
            "STN",
            1,
            0,
            0,
            0
        )
    )
    val filters: LiveData<Filters>
        get() = _filters

    init {
        initData()
    }

    fun initData() {
        error = false
        viewModelScope.launch {
            val text1 = initStations()
            val text2 = initRoute()
            if (!error) _text.value = "$text1\n$text2"
        }
    }

    private suspend fun initStations(): String {
        var newText = ""
        stationsRepository.refresh()?.let {
            _errorText.value = it
            error = true
        }
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

        filters.value?.let {
            routeRepository.refresh(it)?.let { errorText ->
                Log.d("ERROR", errorText)
                _errorText.value = errorText
                error = true
            }
        }
        newText += routeRepository.route.toString()
        return newText
    }
}