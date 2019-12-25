package com.mohsenoid.rickandmorty.data.db

import android.app.Application
import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mohsenoid.rickandmorty.data.mapper.CharacterDbMapper
import com.mohsenoid.rickandmorty.test.CharacterDataFactory
import com.mohsenoid.rickandmorty.test.DataFactory
import com.mohsenoid.rickandmorty.test.EpisodeDataFactory
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBeEmpty
import org.amshove.kluent.shouldNotBeNull
import org.amshove.kluent.shouldNotContain
import org.amshove.kluent.shouldNotEqual
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class DbTest {

    private lateinit var db: Db
    private lateinit var episodeDao: DbEpisodeDao
    private lateinit var characterDao: DbCharacterDao

    @Before
    fun setUp() {
        val application = ApplicationProvider.getApplicationContext<Application>()

        db = Room.inMemoryDatabaseBuilder(application, Db::class.java).build()

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
            val episodes = episodeDao.queryAllEpisodes(1)
            episodes
                .shouldNotBeEmpty()
                .shouldContain(episode)
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
            val episodes = episodeDao.queryAllEpisodes(1)
            episodes
                .shouldNotBeEmpty()
                .shouldContain(updatedEpisode)
                .shouldNotContain(oldEpisode)
        }
    }

    @Test
    fun `test insertCharacter can inserts a new Character`() {
        runBlocking {
            // GIVEN
            val expectedCharacter = CharacterDataFactory.Db.makeDbCharacterModel()

            // WHEN
            characterDao.insertOrUpdateCharacter(expectedCharacter)

            // THEN
            val actualCharacter = characterDao.queryCharacter(expectedCharacter.id)
            actualCharacter
                .shouldNotBeNull()
                .shouldEqual(expectedCharacter)
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
            val actualCharacter = characterDao.queryCharacter(characterId)
            actualCharacter
                .shouldNotBeNull()
                .shouldEqual(updatedCharacter)
                .shouldNotEqual(oldCharacter)
        }
    }

    @Test
    fun `test queryCharacter returns the same Character details inserted`() {
        runBlocking {
            // GIVEN
            val characterId = DataFactory.randomInt()
            val expectedCharacter =
                CharacterDataFactory.Db.makeDbCharacterModel(characterId = characterId)

            // WHEN
            characterDao.insertOrUpdateCharacter(expectedCharacter)

            // THEN
            val actualCharacter = characterDao.queryCharacter(characterId)
            actualCharacter
                .shouldNotBeNull()
                .shouldEqual(expectedCharacter)
        }
    }

    @Test
    fun `test queryCharactersByIds returns the Characters asked for`() {
        runBlocking {
            // GIVEN
            val expected = CharacterDataFactory.Db.makeDbCharacterModel()
            val notExpected = CharacterDataFactory.Db.makeDbCharacterModel()
            val expectedCharacterIds = listOf(expected.id)

            // WHEN
            characterDao.insertOrUpdateCharacter(expected)
            characterDao.insertOrUpdateCharacter(notExpected)

            // THEN
            val characters = characterDao.queryCharactersByIds(expectedCharacterIds)
            characters
                .shouldNotBeEmpty()
                .shouldContain(expected)
                .shouldNotContain(notExpected)
        }
    }

    @Test
    fun `test Character which status is Alive and is not KilledByUser isAlive`() {
        runBlocking {
            // GIVEN
            val expectedCharacterId = DataFactory.randomInt()
            val expectedCharacter =
                CharacterDataFactory.Db.makeDbCharacterModel(
                    characterId = expectedCharacterId,
                    status = CharacterDbMapper.ALIVE,
                    isAlive = true,
                    isKilledByUser = false
                )

            // WHEN
            characterDao.insertOrUpdateCharacter(expectedCharacter)

            // THEN
            val actualCharacter = characterDao.queryCharacter(expectedCharacterId)
            actualCharacter.shouldNotBeNull()
            actualCharacter.status shouldEqual CharacterDbMapper.ALIVE
            actualCharacter.statusAlive.shouldBeTrue()
            actualCharacter.killedByUser.shouldBeFalse()
        }
    }

    @Test
    fun `test user can kill a Character`() {
        runBlocking {
            // GIVEN
            val expectedCharacterId = DataFactory.randomInt()
            val expectedCharacter =
                CharacterDataFactory.Db.makeDbCharacterModel(
                    characterId = expectedCharacterId,
                    status = CharacterDbMapper.ALIVE,
                    isAlive = true,
                    isKilledByUser = false
                )

            // WHEN
            characterDao.insertOrUpdateCharacter(expectedCharacter)
            characterDao.killCharacter(expectedCharacterId)

            // THEN
            val actualCharacter = characterDao.queryCharacter(expectedCharacterId)
            actualCharacter.shouldNotBeNull()
            actualCharacter.status shouldEqual CharacterDbMapper.ALIVE
            actualCharacter.statusAlive.shouldBeTrue()
            actualCharacter.killedByUser.shouldBeTrue()
        }
    }

    @Test
    fun `test a Character which isKilledByUser keeps dead after update by insert`() {
        runBlocking {
            // GIVEN
            val expectedCharacterId = DataFactory.randomInt()
            val expectedCharacter =
                CharacterDataFactory.Db.makeDbCharacterModel(
                    characterId = expectedCharacterId,
                    status = CharacterDbMapper.ALIVE,
                    isAlive = true,
                    isKilledByUser = false
                )

            // WHEN
            characterDao.insertOrUpdateCharacter(expectedCharacter)
            characterDao.killCharacter(expectedCharacterId)
            characterDao.insertOrUpdateCharacter(expectedCharacter)

            // THEN
            val actualCharacter = characterDao.queryCharacter(expectedCharacterId)
            actualCharacter.shouldNotBeNull()
            actualCharacter.status shouldEqual CharacterDbMapper.ALIVE
            actualCharacter.statusAlive.shouldBeTrue()
            actualCharacter.killedByUser.shouldBeTrue()
        }
    }
}
