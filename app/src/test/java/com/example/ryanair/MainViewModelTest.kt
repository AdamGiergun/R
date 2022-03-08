package com.example.ryanair

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.ryanair.db.Station
import com.example.ryanair.network.RyanairApi
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MainViewModel

    private lateinit var apiHelper: RyanairApi

    @Mock
    private lateinit var stationsApiObserver: Observer<List<Station>?>

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        viewModel = MainViewModel()
        viewModel.stations.observeForever(stationsApiObserver)

        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiHelper = RyanairApi
    }

    @After
    fun tearDown() {
        viewModel.stations.removeObserver(stationsApiObserver)
        mockWebServer.shutdown()
    }

    @Test
    fun `read sample stations json file`(){
        val reader = MockResponseFileReader("stations.json")
        assertNotNull(reader.content)
    }
}