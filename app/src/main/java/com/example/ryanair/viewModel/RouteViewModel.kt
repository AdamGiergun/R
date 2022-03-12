package com.example.ryanair.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ryanair.db.Route
import com.example.ryanair.repository.FiltersRepository
import com.example.ryanair.repository.RouteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteViewModel @Inject constructor(
    private val routeRepository: RouteRepository,
    private val filtersRepository: FiltersRepository
) :
    ViewModel() {

    private val _route = MutableLiveData<Route>()
    @Suppress("unused")
    val route: LiveData<Route>
        get() = _route

    var error = false
        private set

    private val _errorText = MutableLiveData<String>()
    val errorText: LiveData<String>
        get() = _errorText

    var errorTextId: Int = 0
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            routeRepository.refresh(filtersRepository.getFilters())
            viewModelScope.launch {
                error = routeRepository.error
                if (error) {
                    errorTextId = routeRepository.errorInfoId
                    _errorText.value = routeRepository.errorText
                } else {
                    _route.value = routeRepository.route
                    // TODO implement RecyclerView
                    _errorText.value = route.value.toString()
                }
            }.join()
        }
    }
}