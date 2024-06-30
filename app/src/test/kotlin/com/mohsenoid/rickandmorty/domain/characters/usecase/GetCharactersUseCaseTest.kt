package com.mohsenoid.rickandmorty.domain.characters.usecase

import com.mohsenoid.rickandmorty.domain.NoInternetConnectionException
import com.mohsenoid.rickandmorty.domain.characters.CharacterRepository
import com.mohsenoid.rickandmorty.util.createCharactersList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetCharactersUseCaseTest {
    private lateinit var characterRepository: CharacterRepository

    private lateinit var useCase: GetCharactersUseCase

    @Before
    fun setUp() {
        characterRepository = mockk()
        useCase = GetCharactersUseCase(characterRepository)
    }

    @Test
    fun `Given repository returns success, When use case is invoked, Then result is success`() =
        runTest {
            // GIVEN
            val expectedCharacters = createCharactersList(TEST_CHARACTERS_SIZE)
            coEvery { characterRepository.getCharacters(TEST_CHARACTERS_IDS) } returns Result.success(expectedCharacters)
            val expectedResult = GetCharactersUseCase.Result.Success(expectedCharacters)

            // WHEN
            val actualResult = useCase(TEST_CHARACTERS_IDS)

            // THEN
            coVerify(exactly = 1) { characterRepository.getCharacters(TEST_CHARACTERS_IDS) }
            kotlin.test.assertEquals(expectedResult, actualResult)
        }

    @Test
    fun `Given repository returns failure caused by NoInternetConnectionException, When use case is invoked, Then result is NoInternetConnection`() =
        runTest {
            // GIVEN
            coEvery { characterRepository.getCharacters(TEST_CHARACTERS_IDS) } returns Result.failure(NoInternetConnectionException("No Internet Connection"))
            val expectedResult = GetCharactersUseCase.Result.NoInternetConnection

            // WHEN
            val actualResult = useCase(TEST_CHARACTERS_IDS)

            // THEN
            coVerify(exactly = 1) { characterRepository.getCharacters(TEST_CHARACTERS_IDS) }
            kotlin.test.assertEquals(expectedResult, actualResult)
        }

    @Test
    fun `Given repository returns failure caused by unknown Exception, When use case is invoked, Then result is Failure`() =
        runTest {
            // GIVEN
            val errorMessage = "Unknown Error"
            coEvery { characterRepository.getCharacters(TEST_CHARACTERS_IDS) } returns Result.failure(Exception(errorMessage))
            val expectedResult = GetCharactersUseCase.Result.Failure(errorMessage)

            // WHEN
            val actualResult = useCase(TEST_CHARACTERS_IDS)

            // THEN
            coVerify(exactly = 1) { characterRepository.getCharacters(TEST_CHARACTERS_IDS) }
            kotlin.test.assertEquals(expectedResult, actualResult)
        }

    companion object {
        private val TEST_CHARACTERS_IDS = setOf(1, 2, 3)
        private val TEST_CHARACTERS_SIZE = TEST_CHARACTERS_IDS.size
    }
}
