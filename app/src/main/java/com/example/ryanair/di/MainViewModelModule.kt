package com.example.ryanair.di

import com.example.ryanair.repository.FiltersRepository
import com.example.ryanair.repository.RouteRepository
import com.example.ryanair.repository.StationsRepository
import com.example.ryanair.viewModel.MainViewModel
import com.example.ryanair.viewModel.RouteViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
class MainViewModelModule {

    @Provides
    @ActivityScoped
    fun providesMainViewModel(
        filtersRepository: FiltersRepository,
        stationsRepository: StationsRepository
    ): MainViewModel {
        return MainViewModel(filtersRepository, stationsRepository)
    }

    @Provides
    @ActivityScoped
    fun providesRouteViewModel(routeRepository: RouteRepository): RouteViewModel {
        return RouteViewModel(routeRepository)
    }
}