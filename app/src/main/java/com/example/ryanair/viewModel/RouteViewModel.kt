package com.example.ryanair.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ryanair.repository.FiltersRepository
import com.example.ryanair.repository.RouteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteViewModel @Inject constructor(
    routeRepository: RouteRepository,
    filtersRepository: FiltersRepository
) :
    ViewModel() {

    val route = routeRepository.route

    val error = routeRepository.error

    val errorText = routeRepository.errorText

    val errorInfoId = routeRepository.errorInfoId

    init {
        filtersRepository.filters.observeForever { filters ->
            viewModelScope.launch {
                filters?.run {
                    routeRepository.refresh(this)
                }
            }
        }
    }
}