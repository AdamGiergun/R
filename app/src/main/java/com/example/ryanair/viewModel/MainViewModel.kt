package com.example.ryanair.viewModel

import androidx.lifecycle.*
import com.example.ryanair.db.Route
import com.example.ryanair.db.SimpleStation
import com.example.ryanair.db.Station
import com.example.ryanair.repository.FiltersRepository
import com.example.ryanair.repository.StationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val filtersRepository: FiltersRepository,
    private val stationsRepository: StationsRepository
) :
    ViewModel() {

    private val _stations = MutableLiveData<List<Station>>()
    val stations: LiveData<List<Station>>
        get() = _stations

    private lateinit var simpleStationArray: Array<SimpleStation>

    val stationsForSpinner
        get() = simpleStationArray.map { it.fullName }.toTypedArray()

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

    val filters = filtersRepository.filters.asLiveData()

    val defaultOrigin: String
        get() {
            val origin: String = filters.value?.origin ?: "DUB"
            return simpleStationArray.first { it.code == origin }.fullName
        }

    val defaultDestination: String
        get() {
            val destination: String = filters.value?.destination ?: "STN"
            return simpleStationArray.first { it.code == destination }.fullName
        }

    fun setOrigin(position: Int) = viewModelScope.launch(Dispatchers.IO) {
        filters.value?.run {
            val newFilters = this.copy(origin = simpleStationArray[position].code)
            filtersRepository.update(newFilters)
        }
    }

    fun setDestination(position: Int) = viewModelScope.launch(Dispatchers.IO) {
        filters.value?.run {
            val newFilters = this.copy(destination = simpleStationArray[position].code)
            filtersRepository.update(newFilters)
        }
    }

    init {
        initStations()
    }

    fun initStations() = viewModelScope.launch {
        error = false
        stationsRepository.run {
            refresh()
            this.let {
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
}