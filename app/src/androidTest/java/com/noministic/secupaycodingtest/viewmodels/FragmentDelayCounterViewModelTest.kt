package com.noministic.secupaycodingtest.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.noministic.secupaycodingtest.data.DefaultRepository
import com.noministic.secupaycodingtest.data.remote.ApiRequests
import com.noministic.secupaycodingtest.getOrAwaitValue
import com.noministic.secupaycodingtest.others.Constants
import junit.framework.TestCase
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class FragmentDelayCounterViewModelTest : TestCase() {

    lateinit var apiRequestInterface: ApiRequests
    lateinit var defaultRepository: DefaultRepository
    lateinit var fragmentDelayCounterViewModel: FragmentDelayCounterViewModel

    @Before
    fun setup() {
        apiRequestInterface = Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false)
                    .build())
            .build().create(ApiRequests::class.java)
        defaultRepository = DefaultRepository(apiRequestInterface)
        fragmentDelayCounterViewModel =
            FragmentDelayCounterViewModel(
                ApplicationProvider.getApplicationContext(),
                defaultRepository
            )
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun mustGetDelayResponse() {
        fragmentDelayCounterViewModel.saveCounterToPrefs(1)
        fragmentDelayCounterViewModel.getDelayCounterResponse()
        val result = fragmentDelayCounterViewModel.delayCounterResponse.getOrAwaitValue()
        val counter = fragmentDelayCounterViewModel.currentCounter.getOrAwaitValue()
        val loading = fragmentDelayCounterViewModel.loading.getOrAwaitValue()

        //counter should be increased to 2 after first successfull call
        assertEquals(2, counter)

        //loading live data should be false
        assertEquals(false, loading)

        //response URL data should be same
        assertEquals(result.url, "https://httpbin.org/delay/1")
    }

    @After
    fun tearTown() {

    }
}