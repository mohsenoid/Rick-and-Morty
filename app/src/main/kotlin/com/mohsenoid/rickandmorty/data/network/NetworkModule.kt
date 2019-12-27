package com.mohsenoid.rickandmorty.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mohsenoid.rickandmorty.BuildConfig
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.net.UnknownHostException
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): HttpUrl {
        return NetworkConstants.BASE_URL.toHttpUrlOrNull()
            ?: throw UnknownHostException("Invalid host: " + NetworkConstants.BASE_URL)
    }

    @Provides
    @Singleton
    fun provideJsonConverterFactory(json: Json): Converter.Factory {
        val contentType = "application/json".toMediaType()
        return json.asConverterFactory(contentType)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG)
                addInterceptor(loggingInterceptor)
        }.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: HttpUrl,
        jsonConverterFactory: Converter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(jsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkClient(retrofit: Retrofit): NetworkClient {
        return retrofit.create(NetworkClient::class.java)
    }
}
