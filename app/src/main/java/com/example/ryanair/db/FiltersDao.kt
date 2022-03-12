package com.example.ryanair.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FiltersDao {
    @Query("SELECT * from Filters WHERE id=1")
    fun get(): Filters

    @Insert
    fun insert(filters: Filters)

    @Update
    fun update(filters: Filters)
}