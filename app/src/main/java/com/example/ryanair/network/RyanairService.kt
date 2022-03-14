package com.example.ryanair.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val STATIONS_URL = " https://mobile-testassets-dev.s3.eu-west-1.amazonaws.com/"
private const val FLIGHTS_BASE_URL = "https://www.ryanair.com/api/booking/v4/en-gb/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofitStations = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(STATIONS_URL)
    .build()

private val retrofitRoute = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(FLIGHTS_BASE_URL)
    .build()

interface StationsApiService {
    @GET("stations.json")
    suspend fun getStationsContainer(): StationsContainer
}

interface RouteApiService {
    @GET("Availability")
    suspend fun getRoute(
        @Query("dateout") dateOut: String,
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("adt") adult: Int,
        @Query("chd") child: Int,
        @Query("teen") teen: Int,
        @Query("inf") infant: Int,
        @Query("ToUs") termsOfUse: String,
        @Query("roundtrip") roundtrip: String
    ): RouteJson
}

object RyanairApi {
    val retrofitStationsApiService: StationsApiService by lazy {
        retrofitStations.create(StationsApiService::class.java)
    }

    val retrofitRouteApiService: RouteApiService by lazy {
        retrofitRoute.create(RouteApiService::class.java)
    }
}