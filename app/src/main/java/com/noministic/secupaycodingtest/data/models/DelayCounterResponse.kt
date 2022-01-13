package com.noministic.secupaycodingtest.data.models


import com.google.gson.annotations.SerializedName

data class DelayCounterResponse(
    @SerializedName("args")
    val args: Args,
    @SerializedName("data")
    val `data`: String,
    @SerializedName("files")
    val files: Files,
    @SerializedName("form")
    val form: Form,
    @SerializedName("headers")
    val headers: Headers,
    @SerializedName("origin")
    val origin: String,
    @SerializedName("url")
    val url: String
)