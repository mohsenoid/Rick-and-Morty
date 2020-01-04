package com.mohsenoid.rickandmorty.injection

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mohsenoid.rickandmorty.data.network.NetworkClient
import com.mohsenoid.rickandmorty.injection.qualifier.QualifiersNames
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import java.net.UnknownHostException

val dataNetworkModule = module {

    single {
        val baseUrl: String = getProperty(QualifiersNames.BASE_URL)
        baseUrl.toHttpUrlOrNull() ?: throw UnknownHostException("Invalid host: $baseUrl")
    }

    single { Json(JsonConfiguration.Stable) }

    single {
        val contentType = "application/json".toMediaType()
        get<Json>().asConverterFactory(contentType)
    }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder().apply {
            val isDebug: Boolean = getProperty(QualifiersNames.IS_DEBUG)
            if (isDebug) {
                val interceptor: HttpLoggingInterceptor = get()
                addInterceptor(interceptor)
            }
        }.build()
    }

    single<Retrofit> {
        val baseUrl: HttpUrl = get()
        val converterFactory: Converter.Factory = get()
        val okHttpClient: OkHttpClient = get()

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

    single<NetworkClient> {
        val retrofit: Retrofit = get()
        retrofit.create(NetworkClient::class.java)
    }
}
