package com.noministic.secupaycodingtest.di

import com.noministic.secupaycodingtest.data.DefaultRepository
import com.noministic.secupaycodingtest.data.remote.ApiRequests
import com.noministic.secupaycodingtest.others.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AuthInterceptorOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SimppleOkHttpClient

/*
    @Singleton
    @Provides
    @AuthInterceptorOkHttpClient
    fun provideRequestInterface(basicAuthenticator: BasicAuthenticator): ApiRequests =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(basicAuthenticator)
                    .build()
            ).addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiRequests::class.java)
*/

    @Singleton
    @Provides
    fun provideSimpleRequestInterface(): ApiRequests =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false)
                    .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

    @Singleton
    @Provides
    fun proviesRepository(apiRequests: ApiRequests): DefaultRepository =
        DefaultRepository(apiRequests)


}