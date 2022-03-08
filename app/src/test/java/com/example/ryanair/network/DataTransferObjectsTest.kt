package com.example.ryanair.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ryanair.MainCoroutineRule
import com.example.ryanair.MockResponseFileReader
import com.squareup.moshi.Moshi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DataTransferObjectsTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `read sample stations json file`() {
        val reader = MockResponseFileReader("stations.json")
        assertThat(reader.content, `is`(notNullValue()))
    }

    @Test
    fun `convert sample stations json file to Station list`() = runTest {
        val reader = MockResponseFileReader("stations.json")
        val moshi: Moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(StationsContainer::class.java)
        val stations = adapter.fromJson(reader.content)?.asDbModel() ?: emptyList()
        assertThat(stations, `is`(notNullValue()))
        assertThat(stations.size, `is`(1))
    }
}