package com.mohsenoid.rickandmorty.domain.characters.usecase

import com.mohsenoid.rickandmorty.domain.characters.CharacterRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UpdateCharacterStatusUseCaseTest {
    private lateinit var characterRepository: CharacterRepository

    private lateinit var useCase: UpdateCharacterStatusUseCase

    @Before
    fun setUp() {
        characterRepository = mockk()
        useCase = UpdateCharacterStatusUseCase(characterRepository)
    }

    @Test
    fun `Given repository returns success, When use case is invoked, Then result is success`() =
        runTest {
            // GIVEN
            coEvery { characterRepository.updateCharacterStatus(TEST_CHARACTER_ID, isKilled = true) } returns Result.success(Unit)
            val expectedResult = UpdateCharacterStatusUseCase.Result.Success

            // WHEN
            val actualResult = useCase(TEST_CHARACTER_ID, isKilled = true)
            // THEN
            kotlin.test.assertEquals(expectedResult, actualResult)
            coVerify(exactly = 1) { characterRepository.updateCharacterStatus(TEST_CHARACTER_ID, isKilled = true) }
        }

    @Test
    fun `Given repository returns failure caused by unknown Exception, When use case is invoked, Then result is Failure`() =
        runTest {
            // GIVEN
            val errorMessage = "Unknown Error"
            coEvery { characterRepository.updateCharacterStatus(TEST_CHARACTER_ID, isKilled = true) } returns Result.failure(Exception(errorMessage))
            val expectedResult = UpdateCharacterStatusUseCase.Result.Failure

            // WHEN
            val actualResult = useCase(TEST_CHARACTER_ID, isKilled = true)

            // THEN
            coVerify(exactly = 1) { characterRepository.updateCharacterStatus(TEST_CHARACTER_ID, isKilled = true) }
            kotlin.test.assertEquals(expectedResult, actualResult)
        }

    companion object {
        private const val TEST_CHARACTER_ID = 1
    }
}
