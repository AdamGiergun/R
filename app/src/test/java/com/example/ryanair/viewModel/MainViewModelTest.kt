package com.example.ryanair.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.ryanair.repository.DummyFiltersRepositoryImpl
import com.example.ryanair.repository.MockStationsRepositoryImpl
import com.example.ryanair.util.MainCoroutineRule
import com.example.ryanair.util.MockResponseFileReader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val mockStationsRepository = MockStationsRepositoryImpl()
    private val dummyFiltersRepositoryImpl = DummyFiltersRepositoryImpl()

    @Test
    fun `provide MainViewModel with sample json file and check stations value`() = runTest {
        mockStationsRepository.reader = MockResponseFileReader("stations.json")
        MainViewModel(dummyFiltersRepositoryImpl, mockStationsRepository).run {
            advanceUntilIdle()

            stations.value.let { viewModelStations ->
                if (viewModelStations == null) {
                    Assert.fail()
                } else {
                    MatcherAssert.assertThat(
                        viewModelStations,
                        `is`(notNullValue())
                    )
                    MatcherAssert.assertThat(viewModelStations.size, `is`(1))
                    MatcherAssert.assertThat(
                        viewModelStations.first().code,
                        `is`("AAL")
                    )
                }
            }
        }
    }

    @Test
    fun `provide MainViewModel with fail json file and check stations value is null end error set`() =
        runTest {
            mockStationsRepository.reader = MockResponseFileReader("fail.json")
            MainViewModel(dummyFiltersRepositoryImpl, mockStationsRepository).run {
                advanceUntilIdle()

                MatcherAssert.assertThat( stations.value, `is`(nullValue()))
                MatcherAssert.assertThat(error, `is`(true))
            }
        }
}