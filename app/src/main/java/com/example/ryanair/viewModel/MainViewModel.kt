package com.example.ryanair.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ryanair.db.SimpleStation
import com.example.ryanair.repository.FiltersRepository
import com.example.ryanair.repository.StationsRepository
import com.example.ryanair.util.DefaultFilters
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

    val stations = stationsRepository.stations

    private lateinit var simpleStationArray: Array<SimpleStation>

    val stationsForSpinner
        get() = simpleStationArray.map { it.fullName }.toTypedArray()

    val error = stationsRepository.error

    val errorText = stationsRepository.errorText

    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text

    val filters = filtersRepository.filters

    val defaultOriginPosition: Int
        get() = getDefaultStationPosition(
            filters.value?.origin ?: DefaultFilters.value.origin
        )

    val defaultDestinationPosition: Int
        get() = getDefaultStationPosition(
            filters.value?.destination ?: DefaultFilters.value.destination
        )

    private fun getDefaultStationPosition(searchedCode: String): Int {
        val element = simpleStationArray.first { it.code == searchedCode }
        return simpleStationArray.indexOf(element)
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

    private val _stationsInitialized = MutableLiveData(false)
    val stationsInitialized: LiveData<Boolean>
        get() = _stationsInitialized

    fun initStations() = viewModelScope.launch {
        stationsRepository.let { sr ->
            sr.refresh()
            if (sr.error.value != true) {
                simpleStationArray = sr.stations.value?.map { station ->
                    SimpleStation(
                        "${station.countryName}, ${station.name}, ${station.code}",
                        station.code
                    )
                }?.sortedBy { it.fullName }?.toTypedArray() ?: emptyArray()
                _stationsInitialized.value = true
            }
        }
    }
}