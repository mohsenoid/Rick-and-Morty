package com.mohsenoid.rickandmorty.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mohsenoid.rickandmorty.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal val remoteModule =
    module {
        single<Gson> {
            GsonBuilder().create()
        }

        single<Converter.Factory> {
            val gson: Gson = get()
            GsonConverterFactory.create(gson)
        }

        single<OkHttpClient> {
            val logging: HttpLoggingInterceptor =
                HttpLoggingInterceptor().apply {
                    level =
                        if (BuildConfig.DEBUG) {
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

        single {
            val retrofit: Retrofit = get()
            retrofit.create(ApiService::class.java)
        }
    }
