/**
 * Big thanks to Filipe Bezerra for getting the idea of parsing nested JSON objects with Moshi
 * https://github.com/filipebezerra/near-earth-asteroids-app-android-kotlin
 * /blob/main/app/src/main/java/dev/filipebezerra/android/nearearthasteroids/datasource/remote/DataTransferObjects.kt
 */

package com.example.ryanair.network

import com.example.ryanair.db.Market
import com.example.ryanair.db.Station
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StationsContainer(
    val stations: List<StationJson>
) {
    @JsonClass(generateAdapter = true)
    data class StationJson(
        val code: String,
        val name: String,
        val alternateName: String?,
        val alias: List<String>,
        val countryCode: String,
        val countryName: String,
        val countryAlias: String?,
        val countryGroupCode: Int,
        val countryGroupName: String,
        val timeZoneCode: String,
        val latitude: String,
        val longitude: String,
        val mobileBoardingPass: Boolean,
        val markets: List<MarketJson>,
        val notices: String?,
        val tripCardImageUrl: String?
    ) {
        @JsonClass(generateAdapter = true)
        data class MarketJson constructor(
            val code: String,
            val group: String?
        )
    }
}

fun StationsContainer.asDbModel(): List<Station> {
    val dbStations = mutableListOf<Station>()

    stations.forEach { stationJson ->
        dbStations.add(
            Station(
                stationJson.code,
                            stationJson.name,
            stationJson.alternateName,
            stationJson.alias,
            stationJson.countryCode,
            stationJson.countryName,
            stationJson.countryAlias,
            stationJson.countryGroupCode,
            stationJson.countryGroupName,
            stationJson.timeZoneCode,
            stationJson.latitude,
            stationJson.longitude,
            stationJson.mobileBoardingPass,
            stationJson.markets.run {
                val markets = mutableListOf<Market>()
                forEach { marketJson ->
                    markets.add(
                        Market(
                            marketJson.code,
                            marketJson.group
                        )
                    )
                }
                markets
            },
            stationJson.notices,
            stationJson.tripCardImageUrl
            )
        )
    }
    return dbStations
}