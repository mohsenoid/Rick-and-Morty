package com.mohsenoid.rickandmorty.data.episodes.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mohsenoid.rickandmorty.data.db.Database
import com.mohsenoid.rickandmorty.data.episodes.db.dao.EpisodeDao
import com.mohsenoid.rickandmorty.util.MainDispatcherRule
import com.mohsenoid.rickandmorty.util.createEpisodeEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class EpisodeDaoTest {
    @get:Rule
    var rule: TestRule = MainDispatcherRule()

    private lateinit var db: Database
    private lateinit var episodeDao: EpisodeDao

    @Before
    fun setUp() {
        stopKoin() // To fix KoinAppAlreadyStartedException

        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, Database::class.java).build()
        episodeDao = db.episodeDao()
    }

    @After
    fun tearDown() {
        db.close()
        stopKoin() // To fix KoinAppAlreadyStartedException
    }

    @Test
    fun `Given episodeEntity inserted, When getEpisodes called, Then result should include episodeEntity`() =
        runTest {
            // Given
            val expectedEntity = createEpisodeEntity(id = 1, page = 0)
            val unexpectedEntity = createEpisodeEntity(id = 2, page = 1)
            episodeDao.insertEpisode(expectedEntity)
            episodeDao.insertEpisode(unexpectedEntity)

            // When
            val actualEpisodes = episodeDao.getEpisodes(page = 0)

            // Then
            assertTrue(actualEpisodes.contains(expectedEntity))
            assertFalse(actualEpisodes.contains(unexpectedEntity))
        }

    @Test
    fun `Given episodeEntity inserted, When getEpisode called, Then result should be episodeEntity`() =
        runTest {
            // Given
            val expectedEntity = createEpisodeEntity(id = 1, page = 0)
            val unexpectedEntity = createEpisodeEntity(id = 2, page = 1)
            episodeDao.insertEpisode(expectedEntity)
            episodeDao.insertEpisode(unexpectedEntity)

            // When
            val actualEpisode = episodeDao.getEpisode(episodeId = expectedEntity.id)

            // Then
            assertEquals(expectedEntity, actualEpisode)
        }
}
