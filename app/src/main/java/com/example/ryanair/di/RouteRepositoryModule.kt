package com.example.ryanair.di

import com.example.ryanair.repository.RouteRepository
import com.example.ryanair.repository.RouteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RouteRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindNavigator(impl: RouteRepositoryImpl): RouteRepository
}