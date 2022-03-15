package com.example.ryanair.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ryanair.db.Filters
import com.example.ryanair.repository.FiltersRepository
import com.example.ryanair.repository.RouteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteViewModel @Inject constructor(
    private val routeRepository: RouteRepository,
) :
    ViewModel() {

    val route = routeRepository.route

    val error = routeRepository.error

    val errorText = routeRepository.errorText

    val errorInfoId = routeRepository.errorInfoId

    private val _filters = MutableLiveData<Filters?>()
    val filters: LiveData<Filters?>
        get() = _filters

    /**
    * There seems to be some problem with testing coroutines,
     * therefore [newFilters] are passed with [refresh]
     * instead of using [FiltersRepository] in init
     */
    fun refresh(newFilters: Filters) {
        _filters.value = newFilters
        viewModelScope.launch {
            newFilters.run {
                routeRepository.refresh(this)
            }
        }
    }
}