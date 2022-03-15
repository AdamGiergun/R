package com.example.ryanair.repository

import com.example.ryanair.util.MockResponseFileReader
import javax.inject.Inject

class FakeRouteRepositoryAtImpl @Inject constructor(): FakeRouteRepository() {

    init {
        refreshRoute(MockResponseFileReader("route.json"))
    }
}