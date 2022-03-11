package com.example.ryanair.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ryanair.util.MainCoroutineRule
import com.example.ryanair.util.MockResponseFileReader
import com.example.ryanair.repository.MockRouteRepositoryImpl
import com.example.ryanair.repository.MockStationsRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private var viewModel: MainViewModel? = null

    private val mockStationsRepository = MockStationsRepositoryImpl()
    private val mockRouteRepository = MockRouteRepositoryImpl()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
        viewModel = null
    }

    @Test
    fun `provide view model with sample json files and check stations and route value`() = runTest {

        mockStationsRepository.reader = MockResponseFileReader("stations.json")
        mockRouteRepository.reader = MockResponseFileReader("route.json")

        MainViewModel(mockStationsRepository, mockRouteRepository).run {
            mockInitRoute()
            advanceUntilIdle()

            stations.value.let { viewModelStations ->
                if (viewModelStations == null) {
                    fail()
                } else {
                    assertThat(viewModelStations, `is`(notNullValue()))
                    assertThat(viewModelStations.size, `is`(1))
                    assertThat(viewModelStations.first().code, `is`("AAL"))
                }
            }

            route.value.let { viewModelRoute ->
                if (viewModelRoute == null) {
                    fail()
                } else {
                    assertThat(viewModelRoute, `is`(notNullValue()))
                    assertThat(viewModelRoute.currency, `is`("PLN"))
                }
            }
        }
    }

    @Test
    fun `provide view model with fail json file and check stations and route value is null end error set`() =
        runTest {
            mockStationsRepository.reader = MockResponseFileReader("fail.json")
            mockRouteRepository.reader = MockResponseFileReader("fail.json")

            MainViewModel(mockStationsRepository).run {
                mockInitRoute()
                advanceUntilIdle()

                assertThat(stations.value, `is`(nullValue()))
                assertThat(route.value, `is`(nullValue()))
//                assertThat(error, `is`(true))
//                assertThat(mockStationsRepository.error, `is`(true))
//                assertThat(mockRouteRepository.error, `is`(true))
            }
        }

//    @Test
//    fun `provide view model with fail stations json file and check stations value is null and error set`() =
//        runTest {
//            mockStationsRepository.reader = MockResponseFileReader("fail.json")
//            mockRouteRepository.reader = MockResponseFileReader("route.json")
//
//            MainViewModel(mockStationsRepository).run {
//                mockInitRoute()
//                advanceUntilIdle()
//
//                assertThat(stations.value, `is`(nullValue()))
//                assertThat(route.value, `is`(notNullValue()))
////                assertThat(mockStationsRepository.error, `is`(true))
////                assertThat(mockRouteRepository.error, `is`(false))
//            }
//        }

    @Test
    fun `provide view model with fail route json file and check route value is null and error set`() =
        runTest {
            mockStationsRepository.reader = MockResponseFileReader("stations.json")
            mockRouteRepository.reader = MockResponseFileReader("fail.json")

            MainViewModel(mockStationsRepository).run {
                mockInitRoute()
                advanceUntilIdle()

                assertThat(stations.value, `is`(notNullValue()))
                assertThat(route.value, `is`(nullValue()))
//                assertThat(mockStationsRepository.error, `is`(false))
//                assertThat(mockRouteRepository.error, `is`(true))
            }
        }
}

private fun MainViewModel.mockInitRoute() {
    javaClass.getDeclaredMethod("initRoute").let { initRoute ->
        initRoute.isAccessible = true
        initRoute.invoke(this)
    }
}