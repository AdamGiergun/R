package com.example.ryanair.network

import com.example.ryanair.db.*
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
    val trips: List<TripJson>,
    val serverTimeUTC: String
) {
    @JsonClass(generateAdapter = true)
    data class TripJson(
        val origin: String,
        val originName: String,
        val destination: String,
        val destinationName: String,
        val routeGroup: String,
        val tripType: String,
        val upgradeType: String,
        val dates: List<DateJson>
    ) {
        @JsonClass(generateAdapter = true)
        data class DateJson(
            val dateOut: String,
            val flights: List<FlightJson>
        ) {
            @JsonClass(generateAdapter = true)
            data class FlightJson(
                val faresLeft: Int,
                val flightKey: String,
                val infantsLeft: Int,
                val regularFare: RegularFareJson?,
                val operatedBy: String,
                val segments: List<SegmentJson>,
                val flightNumber: String,
                val time: List<String>,
                val timeUTC: List<String>,
                val duration: String
            ) {
                @JsonClass(generateAdapter = true)
                data class RegularFareJson(
                    val fareKey: String,
                    val fareClass: String,
                    val fares: List<FareJson>
                ) {
                    @JsonClass(generateAdapter = true)
                    data class FareJson(
                        val type: String,
                        val amount: Float,
                        val count: Int,
                        val hasDiscount: Boolean,
                        val publishedFare: Float,
                        val discountInPercent: Int,
                        val hasPromoDiscount: Boolean,
                        val discountAmount: Float,
                        val hasBogof: Boolean
                    )
                }

                @JsonClass(generateAdapter = true)
                data class SegmentJson(
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

fun RouteJson.asDbModel(): Route = Route(
    termsOfUse,
    currency,
    currPrecision,
    routeGroup,
    tripType,
    upgradeType,
    trips.map { tripJson ->
        Trip(
            tripJson.origin,
            tripJson.originName,
            tripJson.destination,
            tripJson.destinationName,
            tripJson.routeGroup,
            tripJson.tripType,
            tripJson.upgradeType,
            tripJson.dates.map { dateJson ->
                Date(
                    dateJson.dateOut,
                    dateJson.flights.map { flightJson ->
                        Flight(
                            flightJson.faresLeft,
                            flightJson.flightKey,
                            flightJson.infantsLeft,
                            flightJson.regularFare?.let { regularFareJson ->
                                RegularFare(
                                    regularFareJson.fareKey,
                                    regularFareJson.fareClass,
                                    regularFareJson.fares.map { fareJson ->
                                        Fare(
                                            fareJson.type,
                                            fareJson.amount,
                                            fareJson.count,
                                            fareJson.hasDiscount,
                                            fareJson.publishedFare,
                                            fareJson.discountInPercent,
                                            fareJson.hasPromoDiscount,
                                            fareJson.discountAmount,
                                            fareJson.hasBogof
                                        )
                                    }
                                )
                            },
                            flightJson.operatedBy,
                            flightJson.segments.map { segmentJson ->
                                Segment(
                                    segmentJson.segmentNr,
                                segmentJson.origin,
                                segmentJson.destination,
                                segmentJson.flightNumber,
                                segmentJson.time,
                                segmentJson.timeUTC,
                                segmentJson.duration
                                )
                            },
                            flightJson.flightNumber,
                            flightJson.time,
                            flightJson.timeUTC,
                            flightJson.duration
                        )
                    }
                )
            }
        )
    },
    serverTimeUTC
)