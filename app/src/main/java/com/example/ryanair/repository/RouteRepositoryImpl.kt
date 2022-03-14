package com.example.ryanair.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ryanair.R
import com.example.ryanair.db.Filters
import com.example.ryanair.db.Route
import com.example.ryanair.network.RyanairApi
import com.example.ryanair.network.asDbModel
import com.example.ryanair.util.DefaultFilters
import javax.inject.Inject

class RouteRepositoryImpl @Inject constructor() : RouteRepository {

    private val _route = MutableLiveData<Route?>()
    override val route: LiveData<Route?>
        get() = _route

    private val _error = MutableLiveData<Boolean?>()
    override val error: LiveData<Boolean?>
        get() = _error

    private val _errorInfoId = MutableLiveData<Int?>()
    override val errorInfoId: LiveData<Int?>
        get() = _errorInfoId

    private val _errorText = MutableLiveData<String?>()
    override val errorText: LiveData<String?>
        get() = _errorText

    override suspend fun refresh(newFilters: Filters?) {
        _route.value = null
        _error.value = null
        _errorInfoId.value = null
        _errorText.value = null

        try {
            (newFilters ?: DefaultFilters.value).run {
                _route.value = RyanairApi.retrofitRouteApiService.getRoute(
                    dateOut,
                    origin,
                    destination,
                    adult,
                    child,
                    teen,
                    infant,
                    termsOfUse,
                    if (roundtrip) "TRUE" else "FALSE"
                ).asDbModel()
                    .also {
                        if (it.trips[0].dates[0].flights.isEmpty()) {
                            _errorInfoId.value = R.string.error_no_flights_on_selected_date
                            _error.value = true
                        }
                    }
            }
        } catch (e: Exception) {
            _errorInfoId.value = when {
                e.localizedMessage?.contains("HTTP 404") ?: false ->
                    R.string.error_route_not_serviced
                else -> {
                    _errorText.value = e.localizedMessage ?: ""
                    R.string.error_internet
                }
            }
            _error.value = true
            _route.value = null
        }
    }
}


