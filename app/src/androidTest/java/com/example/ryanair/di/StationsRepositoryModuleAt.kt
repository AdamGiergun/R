package com.example.ryanair.di

import com.example.ryanair.repository.MockStationsRepositoryAtImpl
import com.example.ryanair.repository.StationsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [StationsRepositoryModule::class]
)
@Module
abstract class StationsRepositoryModuleAt {

    @Singleton
    @Binds
    abstract fun bindStationsRepository(mockStationsRepositoryAtImpl: MockStationsRepositoryAtImpl): StationsRepository
}