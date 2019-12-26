package com.mohsenoid.rickandmorty.data.db

import android.app.Application
import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
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
        val application: Application = ApplicationProvider.getApplicationContext()

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
            val expectedEpisode: DbEpisodeModel = EpisodeDataFactory.Db.makeEpisode()

            // WHEN
            episodeDao.insertEpisode(expectedEpisode)

            // THEN
            val actualEpisodes: List<DbEpisodeModel> = episodeDao.queryAllEpisodes()

            actualEpisodes
                .shouldNotBeEmpty()
                .shouldContain(expectedEpisode)
        }
    }

    @Test
    fun `test insertEpisode with same ID updates the old one`() {
        runBlocking {
            // GIVEN
            val episodeId: Int = DataFactory.randomInt()
            val oldEpisode: DbEpisodeModel =
                EpisodeDataFactory.Db.makeEpisode(episodeId = episodeId)
            val updatedEpisode: DbEpisodeModel =
                EpisodeDataFactory.Db.makeEpisode(episodeId = episodeId)

            // WHEN
            episodeDao.insertEpisode(oldEpisode)
            episodeDao.insertEpisode(updatedEpisode)

            // THEN
            val actualEpisodes: List<DbEpisodeModel> = episodeDao.queryAllEpisodes()

            actualEpisodes
                .shouldNotBeEmpty()
                .shouldContain(updatedEpisode)
                .shouldNotContain(oldEpisode)
        }
    }

    @Test
    fun `test insertCharacter can inserts a new Character`() {
        runBlocking {
            // GIVEN
            val expectedCharacter: DbCharacterModel = CharacterDataFactory.Db.makeCharacter()

            // WHEN
            characterDao.insertOrUpdateCharacter(expectedCharacter)

            // THEN
            val actualCharacter: DbCharacterModel? =
                characterDao.queryCharacter(expectedCharacter.id)

            actualCharacter
                .shouldNotBeNull()
                .shouldEqual(expectedCharacter)
        }
    }

    @Test
    fun `test insertCharacter with same ID updates the old one`() {
        runBlocking {
            // GIVEN
            val characterId: Int = DataFactory.randomInt()
            val oldCharacter: DbCharacterModel =
                CharacterDataFactory.Db.makeCharacter(characterId = characterId)
            val updatedCharacter: DbCharacterModel =
                CharacterDataFactory.Db.makeCharacter(characterId = characterId)

            // WHEN
            characterDao.insertOrUpdateCharacter(oldCharacter)
            characterDao.insertOrUpdateCharacter(updatedCharacter)

            // THEN
            val actualCharacter: DbCharacterModel? = characterDao.queryCharacter(characterId)

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
            val characterId: Int = DataFactory.randomInt()
            val expectedCharacter: DbCharacterModel =
                CharacterDataFactory.Db.makeCharacter(characterId = characterId)

            // WHEN
            characterDao.insertOrUpdateCharacter(expectedCharacter)

            // THEN
            val actualCharacter: DbCharacterModel? = characterDao.queryCharacter(characterId)

            actualCharacter
                .shouldNotBeNull()
                .shouldEqual(expectedCharacter)
        }
    }

    @Test
    fun `test queryCharactersByIds returns the Characters asked for`() {
        runBlocking {
            // GIVEN
            val expected: DbCharacterModel = CharacterDataFactory.Db.makeCharacter()
            val notExpected: DbCharacterModel = CharacterDataFactory.Db.makeCharacter()
            val expectedCharacterIds: List<Int> = listOf(expected.id)

            // WHEN
            characterDao.insertOrUpdateCharacter(expected)
            characterDao.insertOrUpdateCharacter(notExpected)

            // THEN
            val characters: List<DbCharacterModel> =
                characterDao.queryCharactersByIds(expectedCharacterIds)

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
            val expectedCharacterId: Int = DataFactory.randomInt()
            val expectedCharacter: DbCharacterModel = CharacterDataFactory.Db.makeCharacter(
                characterId = expectedCharacterId,
                status = CharacterDbMapper.ALIVE,
                isAlive = true,
                isKilledByUser = false
            )

            // WHEN
            characterDao.insertOrUpdateCharacter(expectedCharacter)

            // THEN
            val actualCharacter: DbCharacterModel? =
                characterDao.queryCharacter(expectedCharacterId)

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
            val expectedCharacterId: Int = DataFactory.randomInt()
            val expectedCharacter: DbCharacterModel = CharacterDataFactory.Db.makeCharacter(
                characterId = expectedCharacterId,
                status = CharacterDbMapper.ALIVE,
                isAlive = true,
                isKilledByUser = false
            )

            // WHEN
            characterDao.insertOrUpdateCharacter(expectedCharacter)
            characterDao.killCharacter(expectedCharacterId)

            // THEN
            val actualCharacter: DbCharacterModel? =
                characterDao.queryCharacter(expectedCharacterId)

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
            val expectedCharacterId: Int = DataFactory.randomInt()
            val expectedCharacter: DbCharacterModel = CharacterDataFactory.Db.makeCharacter(
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
            val actualCharacter: DbCharacterModel? =
                characterDao.queryCharacter(expectedCharacterId)

            actualCharacter.shouldNotBeNull()
            actualCharacter.status shouldEqual CharacterDbMapper.ALIVE
            actualCharacter.statusAlive.shouldBeTrue()
            actualCharacter.killedByUser.shouldBeTrue()
        }
    }
}
