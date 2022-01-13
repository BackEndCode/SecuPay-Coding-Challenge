package com.noministic.secupaycodingtest.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
class FragmentStatusCodeViewModelTest : TestCase() {

    lateinit var apiRequestInterface: ApiRequests
    lateinit var defaultRepository: DefaultRepository
    lateinit var fragmentDelayCounterViewModel: FragmentStatusCodeViewModel

    @Before
    fun setup() {
        apiRequestInterface = Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .readTimeout(2, TimeUnit.SECONDS)
                    .writeTimeout(2, TimeUnit.SECONDS)
                    .connectTimeout(2, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false)
                    .build()
            )
            .build().create(ApiRequests::class.java)
        defaultRepository = DefaultRepository(apiRequestInterface)
        fragmentDelayCounterViewModel =
            FragmentStatusCodeViewModel(defaultRepository)
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun status_code_300_value_check_should_be_same() {
        fragmentDelayCounterViewModel.getRandomStatusCode("300")
        val result = fragmentDelayCounterViewModel.statusCodeResponse.getOrAwaitValue()
        val loading = fragmentDelayCounterViewModel.loading.getOrAwaitValue()

        //loading live data should be false
        assertEquals(false, loading)

        //response CODE data should be same
        assertEquals(result, "Error code : 300")
    }
    @Test
    fun status_code_400_value_check_should_be_same() {
        fragmentDelayCounterViewModel.getRandomStatusCode("400")
        val result = fragmentDelayCounterViewModel.statusCodeResponse.getOrAwaitValue()
        val loading = fragmentDelayCounterViewModel.loading.getOrAwaitValue()

        //loading live data should be false
        assertEquals(false, loading)

        //response CODE data should be same
        assertEquals(result, "Error code : 400")
    }

    @After
    fun tearTown() {

    }
}