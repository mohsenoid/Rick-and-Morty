package com.mohsenoid.rickandmorty.data.db

import android.app.Application
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mohsenoid.rickandmorty.data.db.model.DbCharacter
import com.mohsenoid.rickandmorty.data.db.model.DbEpisode
import com.mohsenoid.rickandmorty.test.CharacterDataFactory
import com.mohsenoid.rickandmorty.test.DataFactory
import com.mohsenoid.rickandmorty.test.EpisodeDataFactory
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class DbRickAndMortyTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var roomDb: DbRickAndMorty.Db

    private lateinit var db: DbRickAndMorty

    @Before
    fun setUp() {
        val application: Application = ApplicationProvider.getApplicationContext()
        mockkObject(DbRickAndMorty.Db) {
            roomDb = Room.inMemoryDatabaseBuilder(application, DbRickAndMorty.Db::class.java)
                .allowMainThreadQueries()
                .build()

            every { DbRickAndMorty.Db.create(application) } returns roomDb

            db = DbRickAndMorty(application)
        }
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        roomDb.close()
        stopKoin()
    }

    @Test
    fun `insertEpisode can inserts a new Episode`() = runBlockingTest {
        // GIVEN
        val expectedEntityEpisode: DbEpisode = EpisodeDataFactory.makeDbEpisode()

        // WHEN
        db.insertEpisode(expectedEntityEpisode)

        // THEN
        val actualEntityEpisodes: List<DbEpisode> = db.queryAllEpisodes()

        assertFalse(actualEntityEpisodes.isEmpty())
        assertContains(actualEntityEpisodes, expectedEntityEpisode)
    }

    @Test
    fun `insertEpisode with same ID updates the old one`() = runBlockingTest {
        // GIVEN
        val episodeId: Int = DataFactory.randomInt()
        val oldEntityEpisode: DbEpisode =
            EpisodeDataFactory.makeDbEpisode(episodeId = episodeId)
        val updatedEntityEpisode: DbEpisode =
            EpisodeDataFactory.makeDbEpisode(episodeId = episodeId)

        // WHEN
        db.insertEpisode(oldEntityEpisode)
        db.insertEpisode(updatedEntityEpisode)

        // THEN
        val actualEntityEpisodes: List<DbEpisode> = db.queryAllEpisodes()

        assertFalse(actualEntityEpisodes.isEmpty())
        assertContains(actualEntityEpisodes, updatedEntityEpisode)
        // assertNotContains(actualEntityEpisodes, oldEntityEpisode)
    }

    @Test
    fun `insertCharacter can inserts a new Character`() = runBlockingTest {
        // GIVEN
        val expectedCharacter: DbCharacter = CharacterDataFactory.makeDbCharacter()

        // WHEN
        db.insertOrUpdateCharacter(expectedCharacter)

        // THEN
        val actualCharacter: DbCharacter? =
            db.queryCharacter(expectedCharacter.id)

        assertNotNull(actualCharacter)
        assertEquals(actualCharacter, expectedCharacter)
    }

    @Test
    fun `insertCharacter with same ID updates the old one`() = runBlockingTest {
        // GIVEN
        val characterId: Int = DataFactory.randomInt()
        val oldCharacter: DbCharacter =
            CharacterDataFactory.makeDbCharacter(characterId = characterId)
        val updatedCharacter: DbCharacter =
            CharacterDataFactory.makeDbCharacter(characterId = characterId)

        // WHEN
        db.insertOrUpdateCharacter(oldCharacter)
        db.insertOrUpdateCharacter(updatedCharacter)

        // THEN
        val actualCharacter: DbCharacter? = db.queryCharacter(characterId)

        assertNotNull(actualCharacter)
        assertEquals(actualCharacter, updatedCharacter)
    }

    @Test
    fun `queryCharacter returns the same Character details inserted`() = runBlockingTest {
        // GIVEN
        val characterId: Int = DataFactory.randomInt()
        val expectedCharacter: DbCharacter =
            CharacterDataFactory.makeDbCharacter(characterId = characterId)

        // WHEN
        db.insertOrUpdateCharacter(expectedCharacter)

        // THEN
        val actualCharacter: DbCharacter? = db.queryCharacter(characterId)

        assertNotNull(actualCharacter)
        assertEquals(actualCharacter, expectedCharacter)
    }

    @Test
    fun `queryCharactersByIds returns the Characters asked for`() = runBlockingTest {
        // GIVEN
        val expected: DbCharacter = CharacterDataFactory.makeDbCharacter()
        val notExpected: DbCharacter = CharacterDataFactory.makeDbCharacter()
        val expectedCharacterIds: List<Int> = listOf(expected.id)

        // WHEN
        db.insertOrUpdateCharacter(expected)
        db.insertOrUpdateCharacter(notExpected)

        // THEN
        val characters: List<DbCharacter> =
            db.queryCharactersByIds(expectedCharacterIds)

        assertFalse(characters.isEmpty())
        assertContains(characters, expected)
        // assertNotContains(characters, expected)
    }

    @Test
    fun `Character which status is Alive and is not KilledByUser isAlive`() = runBlockingTest {
        // GIVEN
        val expectedCharacterId: Int = DataFactory.randomInt()
        val expectedCharacter: DbCharacter = CharacterDataFactory.makeDbCharacter(
            characterId = expectedCharacterId,
            status = ALIVE,
            isAlive = true,
            isKilledByUser = false,
        )

        // WHEN
        db.insertOrUpdateCharacter(expectedCharacter)

        // THEN
        val actualCharacter: DbCharacter? =
            db.queryCharacter(expectedCharacterId)

        assertNotNull(actualCharacter)
        assertEquals(actualCharacter.status, ALIVE)
        assertTrue(actualCharacter.isAlive)
        assertFalse(actualCharacter.isKilledByUser)
    }

    @Test
    fun `test user can kill a Character`() = runBlockingTest {
        // GIVEN
        val expectedCharacterId: Int = DataFactory.randomInt()
        val expectedCharacter: DbCharacter = CharacterDataFactory.makeDbCharacter(
            characterId = expectedCharacterId,
            status = ALIVE,
            isAlive = true,
            isKilledByUser = false,
        )

        // WHEN
        db.insertOrUpdateCharacter(expectedCharacter)
        db.killCharacter(expectedCharacterId)

        // THEN
        val actualCharacter: DbCharacter? =
            db.queryCharacter(expectedCharacterId)

        assertNotNull(actualCharacter)
        assertEquals(actualCharacter.status, ALIVE)
        assertTrue(actualCharacter.isAlive)
        assertTrue(actualCharacter.isKilledByUser)
    }

    @Test
    fun `test a Character which isKilledByUser keeps dead after update by insert`() =
        runBlockingTest {
            // GIVEN
            val expectedCharacterId: Int = DataFactory.randomInt()
            val expectedCharacter: DbCharacter = CharacterDataFactory.makeDbCharacter(
                characterId = expectedCharacterId,
                status = ALIVE,
                isAlive = true,
                isKilledByUser = false,
            )

            // WHEN
            db.insertOrUpdateCharacter(expectedCharacter)
            db.killCharacter(expectedCharacterId)
            db.insertOrUpdateCharacter(expectedCharacter)

            // THEN
            val actualCharacter: DbCharacter? =
                db.queryCharacter(expectedCharacterId)

            assertNotNull(actualCharacter)
            assertEquals(actualCharacter.status, ALIVE)
            assertTrue(actualCharacter.isAlive)
            assertTrue(actualCharacter.isKilledByUser)
        }

    companion object {
        private const val ALIVE = "alive"
    }
}
