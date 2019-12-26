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

class RepositoryGetCharacterDetailsTest : RepositoryTest() {

    @Test
    fun `test if getCharacterDetails calls networkClient when isOnline`() {
        runBlocking {
            // GIVEN
            val characterId = DataFactory.randomInt()
            stubConfigProviderIsOnline(true)
            stubNetworkClientFetchCharacterDetails(NetworkResponseFactory.CharacterDetails.characterResponse())
            stubCharacterDaoQueryCharacter(CharacterDataFactory.Db.makeDbCharacterModel(characterId))

            // WHEN
            repository.getCharacterDetails(characterId)

            // THEN
            verify(characterDao, times(1)).queryCharacter(characterId)
            verify(networkClient, times(1)).fetchCharacterDetails(characterId)
        }
    }

    @Test
    fun `test if getCharacterDetails calls db only when isOffline`() {
        runBlocking {
            // GIVEN
            val characterId = DataFactory.randomInt()
            stubConfigProviderIsOnline(false)
            stubCharacterDaoQueryCharacter(CharacterDataFactory.Db.makeDbCharacterModel(characterId))

            // WHEN
            repository.getCharacterDetails(characterId)

            // THEN
            verify(characterDao, times(1)).queryCharacter(characterId)
            verify(networkClient, times(0)).fetchCharacterDetails(characterId)
        }
    }

    private suspend fun stubNetworkClientFetchCharacterDetails(character: Response<NetworkCharacterModel>) {
        When calling networkClient.fetchCharacterDetails(any()) itReturns character
    }

    private suspend fun stubCharacterDaoQueryCharacter(character: DbCharacterModel?) {
        When calling characterDao.queryCharacter(any()) itReturns character
    }
}
