package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel
import com.mohsenoid.rickandmorty.test.CharacterDataFactory
import com.mohsenoid.rickandmorty.test.DataFactory
import com.mohsenoid.rickandmorty.test.NetworkResponseFactory
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.When
import org.amshove.kluent.any
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.junit.Test
import retrofit2.Response

class RepositoryGetCharactersTest : RepositoryTest() {

    @Test
    fun `test if getCharactersByIds calls networkClient when isOnline`() {
        runBlocking {
            // GIVEN
            val characterIds = DataFactory.randomIntList(5)
            stubConfigProviderIsOnline(true)
            stubNetworkClientFetchCharactersByIds(NetworkResponseFactory.Characters.charactersResponse())
            stubCharacterDaoQueryCharactersByIds(CharacterDataFactory.Db.makeDbCharactersModelList(5))

            // WHEN
            repository.getCharactersByIds(characterIds)

            // THEN
            verify(characterDao, times(1)).queryCharactersByIds(characterIds)
            verify(networkClient, times(1)).fetchCharactersByIds(characterIds)
        }
    }

    @Test
    fun `test if getCharactersByIds calls db only when isOffline`() {
        runBlocking {
            // GIVEN
            val characterIds = DataFactory.randomIntList(5)
            stubConfigProviderIsOnline(false)
            stubCharacterDaoQueryCharactersByIds(CharacterDataFactory.Db.makeDbCharactersModelList(5))

            // WHEN
            repository.getCharactersByIds(characterIds)

            // THEN
            verify(characterDao, times(1)).queryCharactersByIds(characterIds)
            verify(networkClient, times(0)).fetchCharactersByIds(characterIds)
        }
    }

    private suspend fun stubNetworkClientFetchCharactersByIds(charactersResponse: Response<List<NetworkCharacterModel>>) {
        When calling networkClient.fetchCharactersByIds(any()) itReturns charactersResponse
    }

    private suspend fun stubCharacterDaoQueryCharactersByIds(characters: List<DbCharacterModel>) {
        When calling characterDao.queryCharactersByIds(any()) itReturns characters
    }
}
