package com.example.ryanair.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ryanair.MainCoroutineRule
import com.example.ryanair.MockResponseFileReader
import com.example.ryanair.repository.MockStationsRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert
import org.junit.After
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

    private val repo = MockStationsRepositoryImpl()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
        viewModel = null
    }

    @Test
    fun `provide view model with sample stations json file and check stations value`() = runTest {
        repo.reader = MockResponseFileReader("stations.json")
        MainViewModel(repo).run {
            advanceUntilIdle()
            MatcherAssert.assertThat(stations.value, `is`(notNullValue()))
            stations.value?.let { viewModelStations ->
                MatcherAssert.assertThat(viewModelStations.size, `is`(1))
                MatcherAssert.assertThat(viewModelStations.first().code, `is`("AAL"))
            }
        }
    }

    @Test
    fun `provide view model with fail json file and check stations value is null`() = runTest {
        repo.reader = MockResponseFileReader("fail.json")
        MainViewModel(repo).run {
            advanceUntilIdle()
            MatcherAssert.assertThat(stations.value, `is`(nullValue()))
        }
    }
}