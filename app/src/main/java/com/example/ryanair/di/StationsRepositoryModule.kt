package com.example.ryanair.di

import com.example.ryanair.repository.StationsRepository
import com.example.ryanair.repository.StationsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class StationsRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindNavigator(impl: StationsRepositoryImpl): StationsRepository
}