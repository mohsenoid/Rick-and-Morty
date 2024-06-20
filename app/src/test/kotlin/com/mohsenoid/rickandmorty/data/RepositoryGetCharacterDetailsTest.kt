package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.api.model.ApiCharacter
import com.mohsenoid.rickandmorty.data.api.model.ApiResult
import com.mohsenoid.rickandmorty.data.db.model.DbCharacter
import com.mohsenoid.rickandmorty.test.ApiFactory
import com.mohsenoid.rickandmorty.test.CharacterDataFactory
import com.mohsenoid.rickandmorty.test.DataFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryGetCharacterDetailsTest : RepositoryTest() {

    @Test
    fun `test if getCharacterDetails calls networkClient when isOnline`() = runBlockingTest {
        // GIVEN
        val characterId: Int = DataFactory.randomInt()
        stubStatusProviderIsOnline(isOnline = true)
        stubApiFetchCharacterDetails(ApiFactory.CharacterDetails.makeCharacter())
        stubSbQueryCharacter(
            character = CharacterDataFactory.makeDbCharacter(characterId = characterId),
        )
        stubDbInsertOrUpdateCharacter()

        // WHEN
        repository.getCharacterDetails(characterId = characterId)

        // THEN
        coVerify(exactly = 1) { api.fetchCharacterDetails(characterId = any()) }

        coVerify(exactly = 1) { db.insertOrUpdateCharacter(character = any()) }
        coVerify(exactly = 1) { db.queryCharacter(characterId = any()) }
    }

    @Test
    fun `test if getCharacterDetails calls db only when isOffline`() = runBlockingTest {
        // GIVEN
        val characterId: Int = DataFactory.randomInt()
        stubStatusProviderIsOnline(isOnline = false)
        stubSbQueryCharacter(CharacterDataFactory.makeDbCharacter(characterId = characterId))

        // WHEN
        repository.getCharacterDetails(characterId = characterId)

        // THEN
        coVerify(exactly = 0) { api.fetchCharacterDetails(characterId = any()) }
        coVerify(exactly = 1) { db.queryCharacter(characterId = any()) }
    }

    private suspend fun stubApiFetchCharacterDetails(character: ApiCharacter) {
        coEvery { api.fetchCharacterDetails(characterId = any()) } returns
            ApiResult.Success(character)
    }

    private fun stubDbInsertOrUpdateCharacter() {
        coEvery { db.insertOrUpdateCharacter(any()) } just runs
    }

    private fun stubSbQueryCharacter(character: DbCharacter?) {
        coEvery { db.queryCharacter(characterId = any()) } returns character
    }
}
