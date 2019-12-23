package com.mohsenoid.rickandmorty.data.db

import android.app.Application
import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mohsenoid.rickandmorty.data.mapper.CharacterDbMapper
import com.mohsenoid.rickandmorty.test.CharacterDataFactory
import com.mohsenoid.rickandmorty.test.DataFactory
import com.mohsenoid.rickandmorty.test.EpisodeDataFactory
import java.io.IOException
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class DbTest {

    private lateinit var db: Db
    private lateinit var episodeDao: DbEpisodeDao
    private lateinit var characterDao: DbCharacterDao

    @Before
    fun setUp() {
        val application = ApplicationProvider.getApplicationContext<Application>()

        db = Room.inMemoryDatabaseBuilder(
            application, Db::class.java
        ).build()

        episodeDao = db.episodeDao
        characterDao = db.characterDao
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun `test insertEpisode can inserts a new Episode`() {
        runBlocking {
            // GIVEN
            val episode = EpisodeDataFactory.Db.makeDbEpisodeModel()

            // WHEN
            episodeDao.insertEpisode(episode)

            // THEN
            val result = episodeDao.queryAllEpisodes(1)
            assertTrue(result.contains(episode))
        }
    }

    @Test
    fun `test insertEpisode with same ID updates the old one`() {
        runBlocking {
            // GIVEN
            val episodeId = DataFactory.randomInt()
            val oldEpisode = EpisodeDataFactory.Db.makeDbEpisodeModel(episodeId = episodeId)
            val updatedEpisode = EpisodeDataFactory.Db.makeDbEpisodeModel(episodeId = episodeId)

            // WHEN
            episodeDao.insertEpisode(oldEpisode)
            episodeDao.insertEpisode(updatedEpisode)

            // THEN
            val result = episodeDao.queryAllEpisodes(1)
            assertEquals(result.count(), 1)
            assertFalse(result.contains(oldEpisode))
            assertTrue(result.contains(updatedEpisode))
        }
    }

    @Test
    fun `test insertCharacter can inserts a new Character`() {
        runBlocking {
            // GIVEN
            val character = CharacterDataFactory.Db.makeDbCharacterModel()

            // WHEN
            characterDao.insertOrUpdateCharacter(character)

            // THEN
            val result = characterDao.queryCharacter(character.id)
            assertEquals(result, character)
        }
    }

    @Test
    fun `test insertCharacter with same ID updates the old one`() {
        runBlocking {
            // GIVEN
            val characterId = DataFactory.randomInt()
            val oldCharacter =
                CharacterDataFactory.Db.makeDbCharacterModel(characterId = characterId)
            val updatedCharacter =
                CharacterDataFactory.Db.makeDbCharacterModel(characterId = characterId)

            // WHEN
            characterDao.insertOrUpdateCharacter(oldCharacter)
            characterDao.insertOrUpdateCharacter(updatedCharacter)

            // THEN
            val result = characterDao.queryCharacter(characterId)
            assertNotEquals(result, oldCharacter)
            assertEquals(result, updatedCharacter)
        }
    }

    @Test
    fun `test queryCharacter returns the same Character details inserted`() {
        runBlocking {
            // GIVEN
            val characterId = DataFactory.randomInt()
            val expected = CharacterDataFactory.Db.makeDbCharacterModel(characterId = characterId)

            // WHEN
            characterDao.insertOrUpdateCharacter(expected)

            // THEN
            val actual = characterDao.queryCharacter(characterId)
            assertNotNull(actual)
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `test queryCharactersByIds returns the Characters asked for`() {
        runBlocking {
            // GIVEN
            val expected = CharacterDataFactory.Db.makeDbCharacterModel()
            val notExpected = CharacterDataFactory.Db.makeDbCharacterModel()
            val ids = listOf(expected.id)

            // WHEN
            characterDao.insertOrUpdateCharacter(expected)
            characterDao.insertOrUpdateCharacter(notExpected)

            // THEN
            val result = characterDao.queryCharactersByIds(ids)
            assertEquals(result.count(), 1)
            assertTrue(result.contains(expected))
            assertFalse(result.contains(notExpected))
        }
    }

    @Test
    fun `test Character which status is Alive and is not KilledByUser isAlive`() {
        runBlocking {
            // GIVEN
            val characterId = DataFactory.randomInt()
            val character =
                CharacterDataFactory.Db.makeDbCharacterModel(
                    characterId = characterId,
                    status = CharacterDbMapper.ALIVE,
                    isAlive = true,
                    isKilledByUser = false
                )

            // WHEN
            characterDao.insertOrUpdateCharacter(character)

            // THEN
            val result = characterDao.queryCharacter(character.id)
            assertNotNull(result)
            assertEquals(result!!.status, CharacterDbMapper.ALIVE)
            assertTrue(result.statusAlive)
            assertFalse(result.killedByUser)
        }
    }

    @Test
    fun `test user can kill a Character`() {
        runBlocking {
            // GIVEN
            val characterId = DataFactory.randomInt()
            val character =
                CharacterDataFactory.Db.makeDbCharacterModel(
                    characterId = characterId,
                    status = CharacterDbMapper.ALIVE,
                    isAlive = true,
                    isKilledByUser = false
                )

            // WHEN
            characterDao.insertOrUpdateCharacter(character)
            characterDao.killCharacter(character.id)

            // THEN
            val result = characterDao.queryCharacter(character.id)
            assertNotNull(result)
            assertEquals(result!!.status, CharacterDbMapper.ALIVE)
            assertTrue(result.statusAlive)
            assertTrue(result.killedByUser)
        }
    }

    @Test
    fun `test a Character which isKilledByUser keeps dead after update by insert`() {
        runBlocking {
            // GIVEN
            val characterId = DataFactory.randomInt()
            val character =
                CharacterDataFactory.Db.makeDbCharacterModel(
                    characterId = characterId,
                    status = CharacterDbMapper.ALIVE,
                    isAlive = true,
                    isKilledByUser = false
                )

            // WHEN
            characterDao.insertOrUpdateCharacter(character)
            characterDao.killCharacter(character.id)
            characterDao.insertOrUpdateCharacter(character)

            // THEN
            val result = characterDao.queryCharacter(character.id)
            assertNotNull(result)
            assertEquals(result!!.status, CharacterDbMapper.ALIVE)
            assertTrue(result.statusAlive)
            assertTrue(result.killedByUser)
        }
    }
}
