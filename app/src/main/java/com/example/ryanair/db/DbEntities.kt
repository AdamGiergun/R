package com.example.ryanair.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Station constructor(
    @PrimaryKey val code: String,
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
    val markets: List<Market>,
    val notices: String?,
    val tripCardImageUrl: String?
)

@Entity
data class SimpleStation constructor(
    val fullName: String,
    @PrimaryKey val code: String
)

@Entity
data class Market constructor(
    @PrimaryKey val code: String,
    val group: String?
)

@Entity
data class Route constructor(
    val termsOfUse: String,
    val currency: String,
    val currPrecision: Int,
    val routeGroup: String,
    val tripType: String,
    val upgradeType: String,
    val trips: List<Trip>,
    val serverTimeUTC: String
)

@Entity
data class Trip constructor(
    val origin: String,
    val originName: String,
    val destination: String,
    val destinationName: String,
    val routeGroup: String,
    val tripType: String,
    val upgradeType: String,
    val dates: List<Date>
)

@Entity
data class Date constructor(
    val dateOut: String,
    val flights: List<Flight>
)

@Entity
data class Flight constructor(
    val faresLeft: Int,
    val flightKey: String,
    val infantsLeft: Int,
    val regularFare: RegularFare?,
    val operatedBy: String,
    val segments: List<Segment>,
    val flightNumber: String,
    val time: List<String>,
    val timeUTC: List<String>,
    val duration: String
)

@Entity
data class RegularFare constructor(
    val fareKey: String,
    val fareClass: String,
    val fares: List<Fare>
)

@Entity
data class Fare constructor(
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

@Entity
data class Segment constructor(
    val segmentNr: Int,
    val origin: String,
    val destination: String,
    val flightNumber: String,
    val time: List<String>,
    val timeUTC: List<String>,
    val duration: String
)

@Entity
data class Filters constructor(
    var dateOut: String,
    var origin: String,
    var destination: String,
    var adult: Int,
    var child: Int,
    var teen: Int,
    var infant: Int,
    val termsOfUse: String = "AGREED",
    val roundtrip: Boolean = false
)