package com.example.ryanair.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ryanair.db.Filters
import com.example.ryanair.repository.RouteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteViewModel @Inject constructor(
    private val routeRepository: RouteRepository
) :
    ViewModel() {

    val route = routeRepository.route

    val error = routeRepository.error

    val errorText = routeRepository.errorText

    val errorInfoId = routeRepository.errorInfoId

    fun refreshRoute(filters: Filters?) {
        viewModelScope.launch {
            routeRepository.refresh(filters)
        }
    }
}