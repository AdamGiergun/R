package com.example.ryanair.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ryanair.db.Filters
import com.example.ryanair.db.SimpleStation
import com.example.ryanair.db.Station
import com.example.ryanair.repository.FiltersRepository
import com.example.ryanair.repository.StationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
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

    var error = false
        private set
    private val _errorText = MutableLiveData<String>()
    val errorText: LiveData<String>
        get() = _errorText

    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text

    lateinit var filters: Filters
    private val _filtersInitialized = MutableLiveData(false)
    val filtersInitialized: LiveData<Boolean>
        get() = _filtersInitialized

    val defaultOriginPosition: Int
        get() = getDefaultStationPosition(filters.origin)

    val defaultDestinationPosition: Int
        get() = getDefaultStationPosition(filters.destination)

    private fun getDefaultStationPosition(searchedCode: String): Int {
        val element = simpleStationArray.first { it.code == searchedCode }
        return simpleStationArray.indexOf(element)
    }

    fun setOrigin(position: Int) = viewModelScope.launch(Dispatchers.IO) {
        filters.origin = simpleStationArray[position].code
        filtersRepository.update(filters)
    }

    fun setDestination(position: Int) = viewModelScope.launch(Dispatchers.IO) {
        filters.destination = simpleStationArray[position].code
        filtersRepository.update(filters)
    }

    init {
        viewModelScope.launch {
            listOf(
                viewModelScope.launch(Dispatchers.IO) {
                    filters = filtersRepository.getFilters()
                },
                initStations()
            ).joinAll()
            _filtersInitialized.value = true
        }
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