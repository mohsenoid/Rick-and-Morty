package com.mohsenoid.rickandmorty.data.api

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mohsenoid.rickandmorty.BuildConfig
import com.mohsenoid.rickandmorty.data.api.mapper.ApiResultMapper.toApiResult
import com.mohsenoid.rickandmorty.data.api.model.ApiCharacter
import com.mohsenoid.rickandmorty.data.api.model.ApiEpisodes
import com.mohsenoid.rickandmorty.data.api.model.ApiResult
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.net.UnknownHostException

@OptIn(ExperimentalSerializationApi::class)
internal class ApiRickAndMorty(applicationContext: Context, baseUrl: String) {

    private val api = ApiClient.create(applicationContext, baseUrl)

    suspend fun fetchEpisodes(page: Int): ApiResult<ApiEpisodes> =
        toApiResult {
            api.fetchEpisodes(page)
        }

    suspend fun fetchCharactersByIds(characterIds: String): ApiResult<List<ApiCharacter>> =
        toApiResult {
            api.fetchCharactersByIds(characterIds)
        }

    suspend fun fetchCharacterDetails(characterId: Int): ApiResult<ApiCharacter> =
        toApiResult {
            api.fetchCharacterDetails(characterId)
        }

    internal interface ApiClient {

        @GET(value = EPISODE_ENDPOINT)
        suspend fun fetchEpisodes(
            @Query(value = PARAM_KEY_PAGE)
            page: Int,
        ): Response<ApiEpisodes>

        @GET(value = "$CHARACTER_ENDPOINT{$PATH_KEY_CHARACTER_IDS}")
        suspend fun fetchCharactersByIds(
            @Path(value = PATH_KEY_CHARACTER_IDS)
            characterIds: String,
        ): Response<List<ApiCharacter>>

        @GET(value = "$CHARACTER_ENDPOINT{$PATH_KEY_CHARACTER_ID}")
        suspend fun fetchCharacterDetails(
            @Path(value = PATH_KEY_CHARACTER_ID)
            characterId: Int,
        ): Response<ApiCharacter>

        companion object {
            private const val EPISODE_ENDPOINT: String = "episode/"
            private const val CHARACTER_ENDPOINT: String = "character/"

            private const val PARAM_KEY_PAGE: String = "page"

            private const val PATH_KEY_CHARACTER_IDS: String = "characterIds"
            private const val PATH_KEY_CHARACTER_ID: String = "characterId"

            internal fun create(applicationContext: Context, baseUrl: String): ApiClient {
                val httpUrl = baseUrl.toHttpUrlOrNull()
                    ?: throw UnknownHostException("Invalid host: $baseUrl")

                val contentType = "application/json".toMediaType()
                val converterFactory = Json.asConverterFactory(contentType)

                val loggerInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

                val chuckerInterceptor = ChuckerInterceptor.Builder(applicationContext)
                    .collector(ChuckerCollector(applicationContext))
                    .build()

                val okHttpClient = OkHttpClient.Builder().apply {
                    val isDebug: Boolean = BuildConfig.DEBUG
                    if (isDebug) {
                        addInterceptor(loggerInterceptor)
                    }
                    addInterceptor(chuckerInterceptor)
                }.build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(httpUrl)
                    .addConverterFactory(converterFactory)
                    .client(okHttpClient)
                    .build()

                return retrofit.create(ApiClient::class.java)
            }
        }
    }
}
