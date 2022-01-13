package com.noministic.secupaycodingtest.data.models


import com.google.gson.annotations.SerializedName

data class Headers(
    @SerializedName("Accept")
    val accept: String,
    @SerializedName("Accept-Encoding")
    val acceptEncoding: String,
    @SerializedName("Accept-Language")
    val acceptLanguage: String,
    @SerializedName("Host")
    val host: String,
    @SerializedName("Sec-Ch-Ua")
    val secChUa: String,
    @SerializedName("Sec-Ch-Ua-Mobile")
    val secChUaMobile: String,
    @SerializedName("Sec-Ch-Ua-Platform")
    val secChUaPlatform: String,
    @SerializedName("Sec-Fetch-Dest")
    val secFetchDest: String,
    @SerializedName("Sec-Fetch-Mode")
    val secFetchMode: String,
    @SerializedName("Sec-Fetch-Site")
    val secFetchSite: String,
    @SerializedName("Sec-Fetch-User")
    val secFetchUser: String,
    @SerializedName("Upgrade-Insecure-Requests")
    val upgradeInsecureRequests: String,
    @SerializedName("User-Agent")
    val userAgent: String,
    @SerializedName("X-Amzn-Trace-Id")
    val xAmznTraceId: String
)