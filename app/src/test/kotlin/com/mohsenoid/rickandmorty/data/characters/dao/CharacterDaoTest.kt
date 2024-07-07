package com.mohsenoid.rickandmorty.data.characters.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mohsenoid.rickandmorty.data.characters.db.dao.CharacterDao
import com.mohsenoid.rickandmorty.data.db.Database
import com.mohsenoid.rickandmorty.util.MainDispatcherRule
import com.mohsenoid.rickandmorty.util.createCharacterEntity
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
class CharacterDaoTest {
    @get:Rule
    var rule: TestRule = MainDispatcherRule()

    private lateinit var db: Database
    private lateinit var characterDao: CharacterDao

    @Before
    fun setUp() {
        stopKoin() // To fix KoinAppAlreadyStartedException

        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, Database::class.java).build()
        characterDao = db.characterDao()
    }

    @After
    fun tearDown() {
        db.close()
        stopKoin() // To fix KoinAppAlreadyStartedException
    }

    @Test
    fun `Given characterEntity inserted, When getCharacter called, Then result should be characterEntity`() =
        runTest {
            // Given
            val expectedEntity = createCharacterEntity(id = 1)
            val unexpectedEntity = createCharacterEntity(id = 2)
            characterDao.insertCharacter(expectedEntity)
            characterDao.insertCharacter(unexpectedEntity)

            // When
            val actualEpisode = characterDao.getCharacter(1)

            // Then
            assertEquals(expectedEntity, actualEpisode)
        }

    @Test
    fun `Given characterEntity inserted, When getCharacters called, Then result should include expected characterEntity`() =
        runTest {
            // Given
            val expectedEntity = createCharacterEntity(id = 1)
            val unexpectedEntity = createCharacterEntity(id = 2)
            characterDao.insertCharacter(expectedEntity)
            characterDao.insertCharacter(unexpectedEntity)

            // When
            val actualEpisodes = characterDao.getCharacters(setOf(1))

            // Then
            assertTrue(actualEpisodes.contains(expectedEntity))
            assertFalse(actualEpisodes.contains(unexpectedEntity))
        }

    @Test
    fun `Given characterEntity inserted, When updateCharacterStatus called, Then characterEntity should be updated`() =
        runTest {
            // Given
            val entity1 = createCharacterEntity(id = 1, isKilled = false)
            val entity2 = createCharacterEntity(id = 2, isKilled = false)
            characterDao.insertCharacter(entity1)
            characterDao.insertCharacter(entity2)

            // When
            val updateResult = characterDao.updateCharacterStatus(1, isKilled = true)
            val actualEpisodes = characterDao.getCharacters(setOf(1, 2))

            // Then
            assertEquals(1, updateResult)

            val expectedEntity = entity1.copy(isKilled = true)
            val unexpectedEntity = entity2.copy(isKilled = true)
            assertTrue(actualEpisodes.contains(expectedEntity))
            assertFalse(actualEpisodes.contains(unexpectedEntity))
        }
}
