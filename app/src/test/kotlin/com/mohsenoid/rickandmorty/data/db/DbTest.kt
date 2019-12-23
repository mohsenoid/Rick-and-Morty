package com.mohsenoid.rickandmorty.data.db

import android.app.Application
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.mohsenoid.rickandmorty.data.mapper.CharacterDbMapper
import com.mohsenoid.rickandmorty.test.CharacterDataFactory
import com.mohsenoid.rickandmorty.test.DataFactory
import com.mohsenoid.rickandmorty.test.EpisodeDataFactory
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

    @Before
    fun setUp() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        db = DbImpl(application)
    }

    @Test
    fun `test insertEpisode can inserts a new Episode`() {
        // GIVEN
        val episode = EpisodeDataFactory.Db.makeDbEpisodeModel()

        // WHEN
        db.insertEpisode(episode)

        // THEN
        val result = db.queryAllEpisodes(1)
        assertTrue(result.contains(episode))
    }

    @Test
    fun `test insertEpisode with same ID updates the old one`() {
        // GIVEN
        val episodeId = DataFactory.randomInt()
        val oldEpisode = EpisodeDataFactory.Db.makeDbEpisodeModel(episodeId = episodeId)
        val updatedEpisode = EpisodeDataFactory.Db.makeDbEpisodeModel(episodeId = episodeId)

        // WHEN
        db.insertEpisode(oldEpisode)
        db.insertEpisode(updatedEpisode)

        // THEN
        val result = db.queryAllEpisodes(1)
        assertEquals(result.count(), 1)
        assertFalse(result.contains(oldEpisode))
        assertTrue(result.contains(updatedEpisode))
    }

    @Test
    fun `test insertCharacter can inserts a new Character`() {
        // GIVEN
        val character = CharacterDataFactory.Db.makeDbCharacterModel()

        // WHEN
        db.insertCharacter(character)

        // THEN
        val result = db.queryCharacter(character.id)
        assertEquals(result, character)
    }

    @Test
    fun `test insertCharacter with same ID updates the old one`() {
        // GIVEN
        val characterId = DataFactory.randomInt()
        val oldCharacter =
            CharacterDataFactory.Db.makeDbCharacterModel(characterId = characterId)
        val updatedCharacter =
            CharacterDataFactory.Db.makeDbCharacterModel(characterId = characterId)

        // WHEN
        db.insertCharacter(oldCharacter)
        db.insertCharacter(updatedCharacter)

        // THEN
        val result = db.queryCharacter(characterId)
        assertNotEquals(result, oldCharacter)
        assertEquals(result, updatedCharacter)
    }

    @Test
    fun `test queryCharacter returns the same Character details inserted`() {
        // GIVEN
        val characterId = DataFactory.randomInt()
        val expected = CharacterDataFactory.Db.makeDbCharacterModel(characterId = characterId)

        // WHEN
        db.insertCharacter(expected)

        // THEN
        val actual = db.queryCharacter(characterId)
        assertNotNull(actual)
        assertEquals(expected, actual)
    }

    @Test
    fun `test queryCharactersByIds returns the Characters asked for`() {
        // GIVEN
        val expected = CharacterDataFactory.Db.makeDbCharacterModel()
        val notExpected = CharacterDataFactory.Db.makeDbCharacterModel()
        val ids = listOf(expected.id)

        // WHEN
        db.insertCharacter(expected)
        db.insertCharacter(notExpected)

        // THEN
        val result = db.queryCharactersByIds(ids)
        assertEquals(result.count(), 1)
        assertTrue(result.contains(expected))
        assertFalse(result.contains(notExpected))
    }

    @Test
    fun `test Character which status is Alive and is not KilledByUser isAlive`() {
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
        db.insertCharacter(character)

        // THEN
        val result = db.queryCharacter(character.id)
        assertNotNull(result)
        assertEquals(result!!.status, CharacterDbMapper.ALIVE)
        assertTrue(result.statusAlive)
        assertFalse(result.killedByUser)
    }

    @Test
    fun `test user can kill a Character`() {
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
        db.insertCharacter(character)
        db.killCharacter(character.id)

        // THEN
        val result = db.queryCharacter(character.id)
        assertNotNull(result)
        assertEquals(result!!.status, CharacterDbMapper.ALIVE)
        assertTrue(result.statusAlive)
        assertTrue(result.killedByUser)
    }

    @Test
    fun `test a Character which isKilledByUser keeps dead even after update by insert`() {
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
        db.insertCharacter(character)
        db.killCharacter(character.id)
        db.insertCharacter(character)

        // THEN
        val result = db.queryCharacter(character.id)
        assertNotNull(result)
        assertEquals(result!!.status, CharacterDbMapper.ALIVE)
        assertTrue(result.statusAlive)
        assertTrue(result.killedByUser)
    }
}
