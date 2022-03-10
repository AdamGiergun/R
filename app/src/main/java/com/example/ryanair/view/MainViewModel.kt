package com.example.ryanair.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ryanair.db.Filters
import com.example.ryanair.db.Route
import com.example.ryanair.db.SimpleStation
import com.example.ryanair.db.Station
import com.example.ryanair.repository.RouteRepository
import com.example.ryanair.repository.StationsRepository
import com.example.ryanair.repository.StationsRepositoryImpl
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(private val stationsRepository: StationsRepository = StationsRepositoryImpl())  : ViewModel() {

    private val routeRepository = RouteRepository()

    private val _stations = MutableLiveData<List<Station>>()
    val stations: LiveData<List<Station>>
        get() = _stations

    private lateinit var simpleStationArray: Array<SimpleStation>

    fun getStationsForSpinner() = simpleStationArray.map { it.fullName }.toTypedArray()

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

    fun search() {
        error = false
        initRoute()
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

    fun setOrigin(position: Int) {
        _filters.value?.origin = simpleStationArray[position].code
        _filters.value = filters.value
    }

    fun setDestination(position: Int) {
        _filters.value?.destination = simpleStationArray[position].code
        _filters.value = filters.value
    }

    init {
        initStations()
    }

    fun initStations() = viewModelScope.launch {
        error = false
        stationsRepository.run {
            refresh()
            stationsRepository.let {
                _errorText.value = this.errorText
                this@MainViewModel.error = this.error
                _stations.value = stations
                simpleStationArray = stations?.map {
                    SimpleStation(
                        "${it.countryName}, ${it.name}, ${it.code}",
                        it.code
                    )
                }?.sortedBy { it.fullName }?.toTypedArray() ?: emptyArray()
            }
        }
    }

    private fun initRoute() = viewModelScope.launch {
        error = false
        var newText = ""
        filters.value?.let { filters ->
            routeRepository.run {
                refresh(filters)
                _errorText.value = this.errorText
                this@MainViewModel.error = this.error
                if (!this.error) {
                    this.route?.let { newRoute ->
                        newText += newRoute.toString()
                        _text.value = newText
                        _route.value = newRoute
                    }
                }
            }
        }
    }
}