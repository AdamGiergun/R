package com.example.ryanair.network

import com.example.ryanair.MockResponseFileReader
import com.squareup.moshi.Moshi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataTransferObjectsTest {

    @Test
    fun `read sample stations json file`() {
        val reader = MockResponseFileReader("stations.json")
        assertThat(reader.content, `is`(notNullValue()))
    }

    @Test
    fun `convert sample stations json file to StationJson list`() {
        val reader = MockResponseFileReader("stations.json")
        val moshi: Moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(StationsContainer::class.java)
        val stations = adapter.fromJson(reader.content)?.stations
        assertThat(stations, `is`(notNullValue()))
        assertThat(stations?.size ?: 0, `is`(1))
        assertThat(stations?.first()?.code ?: "", `is`("AAL"))
    }

    @Test
    fun `convert sample stations json file to Station list`() {
        val reader = MockResponseFileReader("stations.json")
        val moshi: Moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(StationsContainer::class.java)
        val stations = adapter.fromJson(reader.content)?.asDbModel() ?: emptyList()
        assertThat(stations, `is`(notNullValue()))
        assertThat(stations.size, `is`(1))
        assertThat(stations.first().code, `is`("AAL"))
    }

    @Test
    fun `read sample route json file`() {
        val reader = MockResponseFileReader("route.json")
        assertThat(reader.content, `is`(notNullValue()))
    }

    @Test
    fun `convert sample route json file to RouteJson`() {
        val reader = MockResponseFileReader("route.json")
        val moshi: Moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(RouteJson::class.java)
        val route = adapter.fromJson(reader.content)
        assertThat(route, `is`(notNullValue()))
        assertThat(route?.termsOfUse, `is`("https://www.ryanair.com/ie/en/corporate/terms-of-use=AGREED"))
    }

    @Test
    fun `convert sample route json file to Route`() {
        val reader = MockResponseFileReader("route.json")
        val moshi: Moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(RouteJson::class.java)
        val route = adapter.fromJson(reader.content)?.asDbModel()
        assertThat(route, `is`(notNullValue()))
        assertThat(route?.termsOfUse ?: "", `is`("https://www.ryanair.com/ie/en/corporate/terms-of-use=AGREED"))
    }
}