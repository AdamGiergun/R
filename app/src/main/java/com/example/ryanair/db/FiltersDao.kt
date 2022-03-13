package com.example.ryanair.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FiltersDao {

    @Query("SELECT * from Filters WHERE id=1")
    fun get(): Flow<Filters?>

    @Insert
    fun insert(filters: Filters)

    @Update
    suspend fun update(filters: Filters)
}