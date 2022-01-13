package com.noministic.secupaycodingtest.data

import com.noministic.secupaycodingtest.data.models.DelayCounterResponse
import com.noministic.secupaycodingtest.data.models.UserAuthResp
import com.noministic.secupaycodingtest.data.remote.ApiRequests
import com.noministic.secupaycodingtest.data.remote.BasicAuthenticator
import com.noministic.secupaycodingtest.data.remote.Repository
import com.noministic.secupaycodingtest.others.Constants
import com.noministic.secupaycodingtest.others.Resource
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DefaultRepository @Inject constructor(private val apiRequests: ApiRequests) : Repository {
    override suspend fun userLogin(username: String, password: String): Resource<UserAuthResp> {
        val apiRequestsWithAuthenticator = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(BasicAuthenticator(username, password))
                    .build()
            ).addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiRequests::class.java)
        return try {
            val response = apiRequestsWithAuthenticator.userLogin(username, password)
            if (response.isSuccessful) {
                response.body()?.let {
                    if (it.authenticated)
                        return Resource.success(it)
                    else return@let Resource.error("authentication failed", null)
                } ?: return Resource.error("unknow error", null)
            } else {
                return Resource.error("unknow error", null)
            }
        } catch (e: Exception) {
            return Resource.error("an unknow error occured", null)
        }
    }

    override suspend fun getHttpStatus(statusCode: String): Resource<Response<Unit?>> {
        return try {
            val response = apiRequests.getHttpStatus(statusCode)
            response.body().let {
                when (response.code()) {
                    200 -> {
                        return@let Resource.success(response)
                    }
                    300 -> {
                        return@let Resource.error("Error code : 300", response)
                    }
                    400 -> {
                        return@let Resource.error("Error code : 400", response)
                    }
                    500 -> {
                        return@let Resource.error("Error code : 500", response)
                    }
                    else -> {
                        return@let Resource.error("Error code unknown", response)
                    }
                }
            }
        } catch (e: Exception) {
            return Resource.error("an unknow error occured", null)
        }
    }

    override suspend fun getDelayCounterResponse(counter: Int): Resource<DelayCounterResponse> {
        return try {
            val response = apiRequests.getDelayCounterResponse(counter)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: return Resource.error("unknown error. code: " + response.code(), null)
            } else {
                return Resource.error("unknown error", null)
            }
        } catch (e: Exception) {
            return Resource.error("an unknow error occured", null)
        }
    }
}