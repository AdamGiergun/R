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

@JsonClass(generateAdapter = true)
data class RouteJson(
    val termsOfUse: String,
    val currency: String,
    val currPrecision: Int,
    val routeGroup: String,
    val tripType: String,
    val upgradeType: String,
    val trips: List<Trip>,
    val serverTimeUTC: String
) {
    @JsonClass(generateAdapter = true)
    data class Trip(
        val origin: String,
        val originName: String,
        val destination: String,
        val destinationName: String,
        val routeGroup: String,
        val tripType: String,
        val upgradeType: String,
        val dates: List<Date>
    ) {
        @JsonClass(generateAdapter = true)
        data class Date(
            val dateOut: String,
            val flights: List<Flight>
        ) {
            @JsonClass(generateAdapter = true)
            data class Flight(
                val faresLeft: Int,
                val flightKey: String,
                val infantsLeft: Int,
                val regularFare: RegularFare,
                val operatedBy: String,
                val segments: List<Segment>,
                val time: List<String>,
                val timeUTC: List<String>,
                val duration: String
            ) {
                @JsonClass(generateAdapter = true)
                data class RegularFare(
                    val fareKey: String,
                    val fareClass: String,
                    val fares: List<Fare>
                ) {
                    @JsonClass(generateAdapter = true)
                    data class Fare(
                        val type: String,
                        val amount: Int,
                        val count: Int,
                        val hasDiscount: Boolean,
                        val publishedFare: Int,
                        val discountInPercent: Int,
                        val hasPromoDiscount: Boolean,
                        val discountAmount: Int,
                        val hasBogof: Boolean
                    )
                }

                @JsonClass(generateAdapter = true)
                data class Segment(
                    val segmentNr: Int,
                    val origin: String,
                    val destination: String,
                    val flightNumber: String,
                    val time: List<String>,
                    val timeUTC: List<String>,
                    val duration: String
                )
            }
        }
    }
}