package com.example.ryanair.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ryanair.R
import com.example.ryanair.db.Filters
import com.example.ryanair.db.Route
import com.example.ryanair.network.RouteJson
import com.example.ryanair.network.asDbModel
import com.example.ryanair.util.MockResponseFileReader
import com.squareup.moshi.Moshi
import javax.inject.Inject

class FakeRouteRepositoryImpl @Inject constructor(): RouteRepository {

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

    override suspend fun refresh(newFilters: Filters?) {}

    fun refresh(reader: MockResponseFileReader) {
        try {
            val moshi: Moshi = Moshi.Builder().build()
            val adapter = moshi.adapter(RouteJson::class.java)
            val routeJson = adapter.fromJson(reader.content)
            val newRoute = routeJson?.asDbModel()
            if (newRoute == null) {
                _errorInfoId.value = R.string.error_route_not_serviced
                _error.value = true
                _route.value = null
            } else {
                _route.value = newRoute
            }
        } catch (e: Exception) {
            _errorText.value = e.localizedMessage ?: "Unknown error"
            _error.value = true
            _route.value = null
        }
    }
}