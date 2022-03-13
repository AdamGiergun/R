package com.example.ryanair.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Database(entities = [Filters::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun filtersDao(): FiltersDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    database.filtersDao().insert(
                        Filters(
                            0,
                            Calendar.getInstance().run {
                                add(Calendar.DAY_OF_YEAR, 30)
                                val formatter =
                                    SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                                formatter.format(time)
                            },
                            "DUB",
                            "STN",
                            1,
                            0,
                            0,
                            0
                        )
                    )
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database.db"
                )
                    .addCallback(
                        AppDatabaseCallback(
                            CoroutineScope(SupervisorJob())
                        )
                    )
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}