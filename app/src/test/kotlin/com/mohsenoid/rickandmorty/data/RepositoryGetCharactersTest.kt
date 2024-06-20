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
class RepositoryGetCharactersTest : RepositoryTest() {

    @Test
    fun `test if getCharactersByIds calls networkClient when isOnline`() = runBlockingTest {
        // GIVEN
        val characterIds: List<Int> = DataFactory.randomIntList(count = 5)
        stubStatusProviderIsOnline(isOnline = true)
        stubApiFetchCharactersByIds(ApiFactory.Characters.makeCharacters())
        stubDbQueryCharactersByIds(CharacterDataFactory.makeDbCharacters(count = 5))
        stubDbInsertOrUpdateCharacter()

        // WHEN
        repository.getCharactersByIds(characterIds = characterIds)

        // THEN
        coVerify(exactly = 1) { api.fetchCharactersByIds(characterIds = any()) }

        coVerify(exactly = 1) { db.insertOrUpdateCharacter(character = any()) }
        coVerify(exactly = 1) { db.queryCharactersByIds(characterIds = any()) }
    }

    @Test
    fun `test if getCharactersByIds calls db only when isOffline`() = runBlockingTest {
        // GIVEN
        val characterIds: List<Int> = DataFactory.randomIntList(count = 5)
        stubStatusProviderIsOnline(isOnline = false)
        stubDbQueryCharactersByIds(CharacterDataFactory.makeDbCharacters(count = 5))

        // WHEN
        repository.getCharactersByIds(characterIds = characterIds)

        // THEN
        coVerify(exactly = 0) { api.fetchCharactersByIds(characterIds = any()) }
        coVerify(exactly = 1) { db.queryCharactersByIds(characterIds = any()) }
    }

    private fun stubApiFetchCharactersByIds(characters: List<ApiCharacter>) {
        coEvery { api.fetchCharactersByIds(characterIds = any()) } returns
            ApiResult.Success(characters)
    }

    private fun stubDbQueryCharactersByIds(characters: List<DbCharacter>) {
        coEvery { db.queryCharactersByIds(characterIds = any()) } returns characters
    }

    private fun stubDbInsertOrUpdateCharacter() {
        coEvery { db.insertOrUpdateCharacter(any()) } just runs
    }
}
