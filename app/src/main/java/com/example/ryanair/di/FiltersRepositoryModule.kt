package com.example.ryanair.di

import com.example.ryanair.repository.FiltersRepository
import com.example.ryanair.repository.FiltersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class FiltersRepositoryModule {

    @Binds
    abstract fun bindFiltersRepository(filtersRepositoryImpl: FiltersRepositoryImpl): FiltersRepository
}