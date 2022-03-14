package com.example.ryanair.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ryanair.repository.DummyFiltersRepositoryImpl
import com.example.ryanair.repository.FakeRouteRepositoryImpl
import com.example.ryanair.util.MainCoroutineRule
import com.example.ryanair.util.MockResponseFileReader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RouteViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val dummyFiltersRepositoryImpl = DummyFiltersRepositoryImpl()
    private val fakeRouteRepositoryImpl = FakeRouteRepositoryImpl()

    @Test
    fun `provide RouteViewModel with route json file and check route value`() =
        runTest {
            fakeRouteRepositoryImpl.refresh(MockResponseFileReader("route.json"))
            RouteViewModel(fakeRouteRepositoryImpl).run {
                refreshRoute(dummyFiltersRepositoryImpl.filters.value)
                advanceUntilIdle()

                assertThat(
                    route.value,
                    `is`(CoreMatchers.notNullValue())
                )
                assertThat(route.value?.currency, `is`("PLN"))
            }
        }

    @Test
    fun `provide RouteViewModel with fail json file and check route value is null and error set`() =
        runTest {
            fakeRouteRepositoryImpl.refresh(MockResponseFileReader("fail.json"))
            RouteViewModel(fakeRouteRepositoryImpl).run {
                refreshRoute(dummyFiltersRepositoryImpl.filters.value)
                advanceUntilIdle()

                assertThat(route.value, `is`(CoreMatchers.nullValue()))
                assertThat(error.value, `is`(true))
                assertThat((errorText.value != null || errorInfoId.value != null), `is`(true))
            }
        }
}