package com.example.ryanair.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ryanair.db.Filters
import com.example.ryanair.db.Route
import com.example.ryanair.repository.RouteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouteViewModel @Inject constructor(private val routeRepository: RouteRepository) :
    ViewModel() {

    private val _route = MutableLiveData<Route>()
    val route: LiveData<Route>
        get() = _route

    var error = false
        private set
//    TODO for future use with separate TextView for error text
//    private val _errorText = MutableLiveData<String>()
//    val errorText: LiveData<String>
//        get() = _errorText

    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text

    fun initRoute(filters: Filters) = viewModelScope.launch {
        error = false
        routeRepository.run {
            refresh(filters)
            this@RouteViewModel.error = this.error
            if (this.error) {
                // TODO separate TextView for error text
                // _errorText.value = this.errorText
                _text.value = this.errorText
            } else {
                _route.value = routeRepository.route
                _text.value = routeRepository.route.toString()
            }
        }
    }
}