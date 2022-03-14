package com.example.ryanair.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ryanair.db.Filters
import com.example.ryanair.db.Route
import com.example.ryanair.repository.RouteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteViewModel @Inject constructor(
    private val routeRepository: RouteRepository
) :
    ViewModel() {

    val route: LiveData<Route?>
        get() = routeRepository.route

    val error : LiveData<Boolean?>
        get() = routeRepository.error

    val errorText: LiveData<String?>
        get() = routeRepository.errorText

    val errorInfoId: LiveData<Int?>
        get() = routeRepository.errorInfoId

    fun refreshRoute(filters: Filters?) {
        viewModelScope.launch {
            routeRepository.refresh(filters)
        }
    }
}