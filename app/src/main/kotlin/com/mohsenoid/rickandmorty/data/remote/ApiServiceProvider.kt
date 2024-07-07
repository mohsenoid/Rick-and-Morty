package com.mohsenoid.rickandmorty.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mohsenoid.rickandmorty.BuildConfig
import com.mohsenoid.rickandmorty.data.db.Database
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object ApiServiceProvider {
    private lateinit var instance: Retrofit

    fun getRetrofit(baseUrl: String): Retrofit {
        if (!::instance.isInitialized) {
            synchronized(Database::class.java) {
                val gson: Gson = GsonBuilder().create()

                val gsonConverterFactory: GsonConverterFactory = GsonConverterFactory.create(gson)

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
                val okHttpClient: OkHttpClient = httpClient.build()

                instance =
                    Retrofit.Builder()
                        .addConverterFactory(gsonConverterFactory)
                        .baseUrl(baseUrl)
                        .client(okHttpClient)
                        .build()
            }
        }
        return instance
    }
}
