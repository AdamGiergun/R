package com.example.ryanair.repository

import com.example.ryanair.R
import com.example.ryanair.db.Filters
import com.example.ryanair.db.Route
import com.example.ryanair.network.RyanairApi
import com.example.ryanair.network.asDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class RouteRepositoryImpl @Inject constructor() : RouteRepository {

    override var route: Route? = null
        private set

    override var error = false
        private set

    override var errorInfoId: Int = 0
        private set

    override var errorText: String = ""
        private set

    override suspend fun refresh(newFilters: Filters?) {
        withContext(Dispatchers.IO) {
            try {
                val filters: Filters = newFilters ?: Filters(
                    0,
                    Calendar.getInstance().run {
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

                filters.run {
                    route = RyanairApi.retrofitRouteApiService.getRoute(
                        dateOut,
                        origin,
                        destination,
                        adult,
                        child,
                        teen,
                        infant,
                        termsOfUse,
                        if (roundtrip) "TRUE" else "FALSE"
                    ).asDbModel()
                        .also {
                            if (it.trips[0].dates[0].flights.isEmpty()) {
                                errorInfoId = R.string.error_no_flights_on_selected_date
                                error = true
                            }
                        }
                }
            } catch (e: Exception) {
                errorInfoId = when {
                    e.localizedMessage?.contains("HTTP 404")
                        ?: false -> R.string.error_route_not_serviced
                    else -> {
                        errorText = e.localizedMessage ?: ""
                        R.string.error_internet
                    }
                }
                error = true
            }
        }
    }
}