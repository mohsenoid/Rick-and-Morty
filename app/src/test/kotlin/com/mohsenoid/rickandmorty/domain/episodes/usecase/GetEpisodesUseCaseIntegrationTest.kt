package com.mohsenoid.rickandmorty.domain.episodes.usecase

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.gson.Gson
import com.mohsenoid.rickandmorty.BASE_URL_QUALIFIER
import com.mohsenoid.rickandmorty.data.dataModules
import com.mohsenoid.rickandmorty.data.episodes.mapper.EpisodeMapper.toEpisode
import com.mohsenoid.rickandmorty.data.episodes.mapper.EpisodeMapper.toEpisodeEntity
import com.mohsenoid.rickandmorty.domain.domainModules
import com.mohsenoid.rickandmorty.util.MainDispatcherRule
import com.mohsenoid.rickandmorty.util.createEpisodeResponse
import com.mohsenoid.rickandmorty.util.createEpisodesRemoteModelList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.robolectric.RobolectricTestRunner
import java.net.HttpURLConnection
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class GetEpisodesUseCaseIntegrationTest : KoinTest {
    @get:Rule
    var rule: TestRule = MainDispatcherRule()

    private lateinit var server: MockWebServer
    private val mockDispatcher =
        object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/episode?page=${TEST_PAGE_SUCCESS}" -> {
                        val response = Gson().toJson((TEST_EPISODES_RESPONSE))
                        MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody(response)
                    }

                    "/episode?page=${TEST_PAGE_FAILURE}" -> {
                        MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
                            .setBody("")
                    }

                    "/episode?page=${TEST_PAGE_END_OF_LIST}" -> {
                        MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
                            .setBody("")
                    }

                    else -> MockResponse().setResponseCode(404)
                }
            }
        }

    private lateinit var useCase: GetEpisodesUseCase

    @Before
    fun setUp() {
        stopKoin() // To fix KoinAppAlreadyStartedException

        server =
            MockWebServer().apply {
                dispatcher = mockDispatcher
                start(8080)
            }

        val baseUrl = server.url("/").toString()
        val appModule = module { single(named(BASE_URL_QUALIFIER)) { baseUrl } }

        startKoin {
            val context: Context = ApplicationProvider.getApplicationContext()
            androidContext(context)
            modules(appModule + domainModules + dataModules)
        }

        useCase = get()
    }

    @After
    fun tearDown() {
        server.shutdown()
        stopKoin()
    }

    @Test
    fun `Given server dispatching HTTP 200, When use case is invoked, Then result is Success`() =
        runTest {
            // GIVEN
            val expectedEpisodes = TEST_EPISODES_REMOTE.map { it.toEpisodeEntity(TEST_PAGE_SUCCESS).toEpisode() }
            val expectedResult = GetEpisodesUseCase.Result.Success(expectedEpisodes)

            // WHEN
            val actualResult = useCase(TEST_PAGE_SUCCESS)

            // THEN
            assertEquals(expectedResult, actualResult)
        }

    @Test
    fun `Given server dispatching HTTP 500, When use case is invoked, Then result is Failure`() =
        runTest {
            // GIVEN
            val expectedResult = GetEpisodesUseCase.Result.Failure("Server Error")

            // WHEN
            val actualResult = useCase(TEST_PAGE_FAILURE)

            // THEN
            assertEquals(expectedResult, actualResult)
        }

    @Test
    fun `Given server dispatching HTTP 404, When use case is invoked, Then result is EndOfList`() =
        runTest {
            // GIVEN
            val expectedResult = GetEpisodesUseCase.Result.EndOfList

            // WHEN
            val actualResult = useCase(TEST_PAGE_END_OF_LIST)

            // THEN
            assertEquals(expectedResult, actualResult)
        }

    companion object {
        private const val TEST_PAGE_SUCCESS = 0
        private const val TEST_PAGE_FAILURE = 1
        private const val TEST_PAGE_END_OF_LIST = 2
        private const val TEST_EPISODES_SIZE = 3
        private val TEST_EPISODES_REMOTE = createEpisodesRemoteModelList(TEST_EPISODES_SIZE)
        private val TEST_EPISODES_RESPONSE = createEpisodeResponse(results = TEST_EPISODES_REMOTE)
    }
}
