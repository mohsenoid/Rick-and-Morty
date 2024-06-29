package com.mohsenoid.rickandmorty.data.characters

import com.mohsenoid.rickandmorty.data.characters.dao.CharacterDao
import com.mohsenoid.rickandmorty.data.characters.entity.CharacterEntity
import com.mohsenoid.rickandmorty.data.characters.mapper.CharacterMapper.toCharacter
import com.mohsenoid.rickandmorty.data.characters.mapper.CharacterMapper.toCharacterEntity
import com.mohsenoid.rickandmorty.data.remote.ApiService
import com.mohsenoid.rickandmorty.data.remote.model.CharacterRemoteModel
import com.mohsenoid.rickandmorty.domain.characters.CharacterRepository
import com.mohsenoid.rickandmorty.domain.characters.model.Character
import com.mohsenoid.rickandmorty.util.createCharacterEntity
import com.mohsenoid.rickandmorty.util.createCharacterRemoteModel
import com.mohsenoid.rickandmorty.util.createCharactersEntityList
import com.mohsenoid.rickandmorty.util.createCharactersRemoteModelList
import com.mohsenoid.rickandmorty.util.createCharactersResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import retrofit2.Response
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CharacterRepositoryImplTest {
    private lateinit var apiService: ApiService
    private lateinit var characterDao: CharacterDao

    private lateinit var repository: CharacterRepository

    @Before
    fun setUp() {
        apiService = mockk()
        characterDao = mockk()

        repository =
            CharacterRepositoryImpl(
                apiService = apiService,
                characterDao = characterDao,
            )
    }

    @Test
    fun `Given db has character, When getCharacter, Then return db character`() =
        runTest {
            // GIVEN
            val characterEntity: CharacterEntity = createCharacterEntity(id = TEST_CHARACTER_ID)
            val expectedCharacter: Character = characterEntity.toCharacter()
            every {
                characterDao.getCharacter(TEST_CHARACTER_ID)
            } returns characterEntity

            // WHEN
            val actualCharacter: Character? = repository.getCharacter(TEST_CHARACTER_ID).getOrNull()

            // THEN
            assertEquals(expectedCharacter, actualCharacter)
            verify(exactly = 1) { characterDao.getCharacter(any()) }
            coVerify(exactly = 0) { apiService.getCharacter(any()) }
        }

    @Test
    fun `Given db has character, When the second time getCharacter, Then return cached character`() =
        runTest {
            // GIVEN
            val characterEntity: CharacterEntity = createCharacterEntity(id = TEST_CHARACTER_ID)
            val expectedCharacter: Character = characterEntity.toCharacter()
            every {
                characterDao.getCharacter(TEST_CHARACTER_ID)
            } returns characterEntity

            // WHEN
            repository.getCharacter(TEST_CHARACTER_ID)

            // THEN
            verify(exactly = 1) { characterDao.getCharacter(any()) }
            coVerify(exactly = 0) { apiService.getCharacter(any()) }

            // WHEN
            val actualCharacter: Character? = repository.getCharacter(TEST_CHARACTER_ID).getOrNull()

            // THEN
            assertEquals(expectedCharacter, actualCharacter)
        }

    @Test
    fun `Given db don't have character, When getCharacter, Then return remote character`() =
        runTest {
            // GIVEN
            val characterRemoteModel: CharacterRemoteModel =
                createCharacterRemoteModel(id = TEST_CHARACTER_ID)
            val characterEntity: CharacterEntity = characterRemoteModel.toCharacterEntity()
            val expectedCharacter: Character = characterEntity.toCharacter()
            every {
                characterDao.getCharacter(TEST_CHARACTER_ID)
            } returns null
            every {
                characterDao.insertCharacter(characterEntity)
            } just runs
            coEvery {
                apiService.getCharacter(TEST_CHARACTER_ID)
            } returns Response.success(characterRemoteModel)

            // WHEN
            val actualCharacter: Character? = repository.getCharacter(TEST_CHARACTER_ID).getOrNull()

            // THEN
            assertEquals(expectedCharacter, actualCharacter)
            verify(exactly = 1) { characterDao.getCharacter(any()) }
            coVerify(exactly = 1) { apiService.getCharacter(any()) }
            coVerify(exactly = 1) { characterDao.insertCharacter(any()) }
        }

    @Test
    fun `Given db has all characters, When getCharacters, Then return db characters`() =
        runTest {
            // GIVEN
            val characterEntities: List<CharacterEntity> =
                createCharactersEntityList(
                    count = TEST_CHARACTERS_SIZE,
                    id = { TEST_CHARACTERS_IDS[it] },
                )
            val expectedCharacters: List<Character> = characterEntities.map { it.toCharacter() }
            every {
                characterDao.getCharacters(TEST_CHARACTERS_IDS.toSet())
            } returns characterEntities

            // WHEN
            val actualCharacters: Set<Character>? =
                repository.getCharacters(TEST_CHARACTERS_IDS.toSet()).getOrNull()

            // THEN
            assertContentEquals(expectedCharacters, actualCharacters)
            verify(exactly = 1) { characterDao.getCharacters(any()) }
            coVerify(exactly = 0) { apiService.getCharacters(any()) }
        }

    @Test
    fun `Given db has all characters, When the second time getCharacters, Then return cached characters`() =
        runTest {
            // GIVEN
            val characterEntities: List<CharacterEntity> =
                createCharactersEntityList(
                    count = TEST_CHARACTERS_SIZE,
                    id = { TEST_CHARACTERS_IDS[it] },
                )
            val expectedCharacters: List<Character> = characterEntities.map { it.toCharacter() }
            every {
                characterDao.getCharacters(TEST_CHARACTERS_IDS.toSet())
            } returns characterEntities

            // WHEN
            repository.getCharacters(TEST_CHARACTERS_IDS.toSet()).getOrNull()

            // THEN
            verify(exactly = 1) { characterDao.getCharacters(any()) }
            coVerify(exactly = 0) { apiService.getCharacters(any()) }

            // WHEN
            val actualCharacters: Set<Character>? =
                repository.getCharacters(TEST_CHARACTERS_IDS.toSet()).getOrNull()

            // THEN
            assertContentEquals(expectedCharacters, actualCharacters)
        }

    @Test
    fun `Given db has some characters, When getCharacters, Then return remote characters`() =
        runTest {
            // GIVEN
            val charactersRemoteModel: List<CharacterRemoteModel> =
                createCharactersRemoteModelList(
                    count = TEST_CHARACTERS_SIZE,
                    id = { TEST_CHARACTERS_IDS[it] },
                )
            val characterEntities: List<CharacterEntity> =
                charactersRemoteModel.map { it.toCharacterEntity() }
            val expectedCharacters: List<Character> = characterEntities.map { it.toCharacter() }

            val characterResponse = createCharactersResponse(characters = charactersRemoteModel)
            val dbCharacters = listOf(characterEntities[0])
            every {
                characterDao.getCharacters(TEST_CHARACTERS_IDS.toSet())
            } returns dbCharacters
            characterEntities.forEach { characterEntity ->
                every {
                    characterDao.insertCharacter(characterEntity)
                } just runs
            }
            val pendingCharactersIds = TEST_CHARACTERS_IDS - dbCharacters.map { it.id }.toSet()
            val pendingCharactersIdsString = pendingCharactersIds.joinToString(",")
            coEvery {
                apiService.getCharacters(pendingCharactersIdsString)
            } returns Response.success(characterResponse)

            // WHEN
            val actualCharacters: Set<Character>? =
                repository.getCharacters(TEST_CHARACTERS_IDS.toSet()).getOrNull()

            // THEN
            assertContentEquals(expectedCharacters, actualCharacters)
            verify(exactly = 1) { characterDao.getCharacters(any()) }
            coVerify(exactly = 1) { apiService.getCharacters(any()) }
            coVerify(exactly = TEST_CHARACTERS_SIZE) { characterDao.insertCharacter(any()) }
        }

    @Test
    fun `Given db has character, When updateCharacterStatus, Then return true`() =
        runTest {
            // GIVEN
            every {
                characterDao.updateCharacterStatus(TEST_CHARACTER_ID, true)
            } returns 1

            // WHEN
            val updateResult = repository.updateCharacterStatus(TEST_CHARACTER_ID, true)

            // THEN
            assertTrue(updateResult.isSuccess)
            verify(exactly = 1) { characterDao.updateCharacterStatus(any(), any()) }
        }

    @Test
    fun `Given db has character, When updateCharacterStatus, Then second call return updated character from cache`() =
        runTest {
            // GIVEN
            val characterEntity: CharacterEntity =
                createCharacterEntity(id = TEST_CHARACTER_ID, isKilled = false)
            val expectedCharacter: Character = characterEntity.toCharacter().copy(isKilled = true)

            every {
                characterDao.updateCharacterStatus(TEST_CHARACTER_ID, true)
            } returns 1
            every {
                characterDao.getCharacter(TEST_CHARACTER_ID)
            } returns characterEntity.copy(isKilled = true)

            // WHEN
            repository.updateCharacterStatus(TEST_CHARACTER_ID, true)
            val actualCharacter: Character? = repository.getCharacter(TEST_CHARACTER_ID).getOrNull()

            // THEN
            assertEquals(expectedCharacter, actualCharacter)
            verify(exactly = 1) { characterDao.updateCharacterStatus(any(), any()) }
            verify(exactly = 1) { characterDao.getCharacter(any()) }
        }

    companion object {
        private const val TEST_CHARACTER_ID = 1
        private val TEST_CHARACTERS_IDS = listOf(1, 2, 3)
        private val TEST_CHARACTERS_SIZE = TEST_CHARACTERS_IDS.size
    }
}
