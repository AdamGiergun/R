package com.example.ryanair.di

import com.example.ryanair.repository.FiltersRepository
import com.example.ryanair.repository.StubFiltersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [FiltersRepositoryModule::class]
)
@Module
abstract class FiltersRepositoryModuleAt {

    @Binds
    @Singleton
    abstract fun bindFiltersRepository(stubFiltersRepositoryImpl: StubFiltersRepositoryImpl): FiltersRepository
}