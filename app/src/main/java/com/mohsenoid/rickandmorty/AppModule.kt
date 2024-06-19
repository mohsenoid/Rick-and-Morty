package com.mohsenoid.rickandmorty

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {

    single {
        GsonBuilder().create()
    }

    single<Converter.Factory> {
        GsonConverterFactory.create(get())
    }

    single<OkHttpClient> {
        val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.build()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(get())
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(get())
            .build()
    }
}
