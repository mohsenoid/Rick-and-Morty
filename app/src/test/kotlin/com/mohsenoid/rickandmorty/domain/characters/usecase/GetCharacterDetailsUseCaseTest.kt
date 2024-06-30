package com.mohsenoid.rickandmorty.domain.characters.usecase

import com.mohsenoid.rickandmorty.domain.NoInternetConnectionException
import com.mohsenoid.rickandmorty.domain.characters.CharacterRepository
import com.mohsenoid.rickandmorty.util.createCharacter
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetCharacterDetailsUseCaseTest {
    private lateinit var characterRepository: CharacterRepository

    private lateinit var useCase: GetCharacterDetailsUseCase

    @Before
    fun setUp() {
        characterRepository = mockk()
        useCase = GetCharacterDetailsUseCase(characterRepository)
    }

    @Test
    fun `Given repository returns success, When use case is invoked, Then result is success`() =
        runTest {
            // GIVEN
            val expectedCharacter = createCharacter(id = TEST_CHARACTER_ID)
            coEvery { characterRepository.getCharacter(TEST_CHARACTER_ID) } returns Result.success(expectedCharacter)
            val expectedResult = GetCharacterDetailsUseCase.Result.Success(expectedCharacter)

            // WHEN
            val actualResult = useCase(TEST_CHARACTER_ID)
            // THEN
            assertEquals(expectedResult, actualResult)
            coVerify(exactly = 1) { characterRepository.getCharacter(TEST_CHARACTER_ID) }
        }

    @Test
    fun `Given repository returns failure caused by NoInternetConnectionException, When use case is invoked, Then result is NoInternetConnection`() =
        runTest {
            // GIVEN
            coEvery { characterRepository.getCharacter(TEST_CHARACTER_ID) } returns Result.failure(NoInternetConnectionException("No Internet Connection"))
            val expectedResult = GetCharacterDetailsUseCase.Result.NoInternetConnection

            // WHEN
            val actualResult = useCase(TEST_CHARACTER_ID)

            // THEN
            coVerify(exactly = 1) { characterRepository.getCharacter(TEST_CHARACTER_ID) }
            assertEquals(expectedResult, actualResult)
        }

    @Test
    fun `Given repository returns failure caused by unknown Exception, When use case is invoked, Then result is Failure`() =
        runTest {
            // GIVEN
            val errorMessage = "Unknown Error"
            coEvery { characterRepository.getCharacter(TEST_CHARACTER_ID) } returns Result.failure(Exception(errorMessage))
            val expectedResult = GetCharacterDetailsUseCase.Result.Failure(errorMessage)

            // WHEN
            val actualResult = useCase(TEST_CHARACTER_ID)

            // THEN
            coVerify(exactly = 1) { characterRepository.getCharacter(TEST_CHARACTER_ID) }
            assertEquals(expectedResult, actualResult)
        }

    companion object {
        private const val TEST_CHARACTER_ID = 1
    }
}
