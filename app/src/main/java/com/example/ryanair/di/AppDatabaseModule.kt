package com.example.ryanair.di

import android.content.Context
import com.example.ryanair.db.AppDatabase
import com.example.ryanair.db.FiltersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getDatabase(appContext)
    }

    @Provides
    fun provideFiltersDao(appDatabase: AppDatabase): FiltersDao {
        return appDatabase.filtersDao()
    }
}