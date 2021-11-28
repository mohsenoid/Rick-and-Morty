package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.api.model.ApiCharacter
import com.mohsenoid.rickandmorty.data.db.model.DbCharacter
import com.mohsenoid.rickandmorty.test.CharacterDataFactory
import com.mohsenoid.rickandmorty.test.DataFactory
import com.mohsenoid.rickandmorty.test.NetworkResponseFactory
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.Verify
import org.amshove.kluent.VerifyNoFurtherInteractions
import org.amshove.kluent.VerifyNoInteractions
import org.amshove.kluent.When
import org.amshove.kluent.any
import org.amshove.kluent.called
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.amshove.kluent.on
import org.amshove.kluent.that
import org.amshove.kluent.was
import org.junit.Test
import retrofit2.Response

class RepositoryGetCharactersTest : RepositoryTest() {

    @Test
    fun `test if getCharactersByIds calls networkClient when isOnline`() {
        runBlocking {
            // GIVEN
            val characterIds: List<Int> = DataFactory.randomIntList(count = 5)
            stubConfigProviderIsOnline(isOnline = true)
            stubNetworkClientFetchCharactersByIds(NetworkResponseFactory.Characters.charactersResponse())
            stubCharacterDaoQueryCharactersByIds(CharacterDataFactory.Db.makeCharacters(count = 5))

            // WHEN
            repository.getCharactersByIds(characterIds = characterIds)

            // THEN
            Verify on networkClient that networkClient.fetchCharactersByIds(characterIds = any())
            VerifyNoFurtherInteractions on networkClient

            Verify on characterDao that characterDao.insertOrUpdateCharacter(character = any()) was called
            Verify on characterDao that characterDao.queryCharactersByIds(characterIds = any()) was called
            VerifyNoFurtherInteractions on characterDao
        }
    }

    @Test
    fun `test if getCharactersByIds calls db only when isOffline`() {
        runBlocking {
            // GIVEN
            val characterIds: List<Int> = DataFactory.randomIntList(count = 5)
            stubConfigProviderIsOnline(isOnline = false)
            stubCharacterDaoQueryCharactersByIds(CharacterDataFactory.Db.makeCharacters(count = 5))

            // WHEN
            repository.getCharactersByIds(characterIds = characterIds)

            // THEN
            VerifyNoInteractions on networkClient

            Verify on characterDao that characterDao.queryCharactersByIds(characterIds = any()) was called
            VerifyNoFurtherInteractions on characterDao
        }
    }

    private suspend fun stubNetworkClientFetchCharactersByIds(charactersResponse: Response<List<ApiCharacter>>) {
        When calling networkClient.fetchCharactersByIds(characterIds = any()) itReturns charactersResponse
    }

    private suspend fun stubCharacterDaoQueryCharactersByIds(characters: List<DbCharacter>) {
        When calling characterDao.queryCharactersByIds(characterIds = any()) itReturns characters
    }
}
