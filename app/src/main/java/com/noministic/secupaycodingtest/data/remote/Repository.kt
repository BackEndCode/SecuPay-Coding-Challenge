package com.noministic.secupaycodingtest.data.remote

import com.noministic.secupaycodingtest.data.models.DelayCounterResponse
import com.noministic.secupaycodingtest.data.models.UserAuthResp
import com.noministic.secupaycodingtest.others.Constants.DEFAULT_STATUS_CODES
import com.noministic.secupaycodingtest.others.Resource
import retrofit2.Response

interface Repository {
    suspend fun userLogin(
        username: String, password: String
    ): Resource<UserAuthResp>

    suspend fun getHttpStatus(statusCode:String=DEFAULT_STATUS_CODES): Resource<Response<Unit?>>
    suspend fun getDelayCounterResponse(counter: Int): Resource<DelayCounterResponse>

}