package com.example.ryanair.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ryanair.repository.FakeFiltersRepositoryImpl
import com.example.ryanair.repository.MockStationsRepositoryImpl
import com.example.ryanair.util.MainCoroutineRule
import com.example.ryanair.util.MockResponseFileReader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val mockStationsRepository = MockStationsRepositoryImpl()
    private val fakeFiltersRepositoryImpl = FakeFiltersRepositoryImpl()

    @Test
    fun `provide MainViewModel with sample json file and check stations value`() = runTest {
        mockStationsRepository.reader = MockResponseFileReader("stations.json")
        MainViewModel(fakeFiltersRepositoryImpl, mockStationsRepository).run {
            advanceUntilIdle()

            stations.value.let { viewModelStations ->
                if (viewModelStations == null) {
                    Assert.fail()
                } else {
                    MatcherAssert.assertThat(
                        viewModelStations,
                        CoreMatchers.`is`(CoreMatchers.notNullValue())
                    )
                    MatcherAssert.assertThat(viewModelStations.size, CoreMatchers.`is`(1))
                    MatcherAssert.assertThat(
                        viewModelStations.first().code,
                        CoreMatchers.`is`("AAL")
                    )
                }
            }
        }
    }

    @Test
    fun `provide MainViewModel with fail json file and check stations value is null end error set`() =
        runTest {
            mockStationsRepository.reader = MockResponseFileReader("fail.json")
            MainViewModel(fakeFiltersRepositoryImpl, mockStationsRepository).run {
                advanceUntilIdle()

                MatcherAssert.assertThat(
                    stations.value,
                    CoreMatchers.`is`(CoreMatchers.nullValue())
                )
                MatcherAssert.assertThat(error, CoreMatchers.`is`(true))
            }
        }
}