package com.example.ryanair.di

import com.example.ryanair.repository.FakeRouteRepositoryAtImpl
import com.example.ryanair.repository.RouteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RouteRepositoryModule::class]
)
@Module
abstract class RouteRepositoryModuleAt {

    @Binds
    @Singleton
    abstract fun bindRouteRepository(fakeRouteRepositoryAtImpl: FakeRouteRepositoryAtImpl): RouteRepository
}