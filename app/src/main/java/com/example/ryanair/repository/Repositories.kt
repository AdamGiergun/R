package com.example.ryanair.repository

import javax.inject.Inject

class Repositories @Inject constructor (val filtersRepository: FiltersRepository){
    var stationsRepository: StationsRepository = StationsRepositoryImpl()
    var routeRepository : RouteRepository = RouteRepositoryImpl()
}