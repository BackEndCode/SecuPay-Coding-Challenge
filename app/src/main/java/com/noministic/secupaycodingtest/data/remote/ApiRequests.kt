package com.noministic.secupaycodingtest.data.remote

import com.noministic.secupaycodingtest.data.models.DelayCounterResponse
import com.noministic.secupaycodingtest.data.models.UserAuthResp
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRequests {

    @GET("/basic-auth/{username}/{passwd}")
    suspend fun userLogin(
        @Path("username") username: String,
        @Path("passwd") password: String
    ): Response<UserAuthResp>

    @GET("/status/{code}")
    suspend fun getHttpStatus(@Path("code") code: String = "200,300,400,500"): Response<Unit?>

    @GET("/delay/{counter}")
    suspend fun getDelayCounterResponse(@Path("counter") counter: Int): Response<DelayCounterResponse>
}