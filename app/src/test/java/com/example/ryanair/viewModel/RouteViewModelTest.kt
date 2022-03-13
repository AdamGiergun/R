package com.example.ryanair.viewModel

//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import com.example.ryanair.repository.FakeFiltersRepositoryImpl
//import com.example.ryanair.repository.MockRouteRepositoryImpl
//import com.example.ryanair.util.MainCoroutineRule
//import com.example.ryanair.util.MockResponseFileReader
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.test.TestDispatcher
//import kotlinx.coroutines.test.advanceUntilIdle
//import kotlinx.coroutines.test.runTest
//import org.hamcrest.CoreMatchers
//import org.hamcrest.MatcherAssert
//import org.junit.Rule
//import org.junit.Test
//
//@ExperimentalCoroutinesApi
class RouteViewModelTest {
/*
      RouteViewModel presently untestable due to use of nested coroutines
*/

//    @get:Rule
//    var instantExecutorRule = InstantTaskExecutorRule()
//
//    @get:Rule
//    var mainCoroutineRule = MainCoroutineRule()
//
//    private val mockRouteRepository = MockRouteRepositoryImpl()
//    private val fakeFiltersRepositoryImpl = FakeFiltersRepositoryImpl()
//
//    @Test
//    fun `provide RouteViewModel with route json file and check route value`() =
//        runTest {
//            mockRouteRepository.reader = MockResponseFileReader("route.json")
//            advanceUntilIdle()
//            RouteViewModel(mockRouteRepository, fakeFiltersRepositoryImpl).run {
//                advanceUntilIdle()
//
//                MatcherAssert.assertThat(
//                    route.value,
//                    CoreMatchers.`is`(CoreMatchers.notNullValue())
//                )
//                MatcherAssert.assertThat(route.value?.currency, CoreMatchers.`is`("PLN"))
//            }
//        }
//
//    @Test
//    fun `provide RouteViewModel with fail json file and check route value is null and error set`() =
//        runTest {
//            mockRouteRepository.initRoute(MockResponseFileReader("fail.json"))
//            RouteViewModel(mockRouteRepository).run {
//                refreshRoute(fakeFiltersRepositoryImpl.filters.first())
//                advanceUntilIdle()
//
//                MatcherAssert.assertThat(route.value, CoreMatchers.`is`(CoreMatchers.nullValue()))
//                MatcherAssert.assertThat(error, CoreMatchers.`is`(true))
//            }
//        }
}