package com.noministic.secupaycodingtest.data.models


import com.google.gson.annotations.SerializedName

data class UserAuthResp(
    @SerializedName("authenticated")
    val authenticated: Boolean,
    @SerializedName("user")
    val user: String
)