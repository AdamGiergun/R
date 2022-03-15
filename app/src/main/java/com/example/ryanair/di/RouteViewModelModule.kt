package com.example.ryanair.di

import com.example.ryanair.repository.RouteRepository
import com.example.ryanair.viewModel.RouteViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

@InstallIn(FragmentComponent::class)
@Module
class RouteViewModelModule {

    @Provides
    @FragmentScoped
    fun providesRouteViewModel(
        routeRepository: RouteRepository
    ): RouteViewModel {
        return RouteViewModel(routeRepository)
    }
}