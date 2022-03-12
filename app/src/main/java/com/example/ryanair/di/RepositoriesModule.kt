package com.example.ryanair.di

import com.example.ryanair.repository.FiltersRepository
import com.example.ryanair.repository.Repositories
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class RepositoriesModule {

    @Provides
    fun provideRepositories(filtersRepository: FiltersRepository): Repositories {
        return Repositories(filtersRepository)
    }
}