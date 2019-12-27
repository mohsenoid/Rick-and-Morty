package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel
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

class RepositoryGetCharacterDetailsTest : RepositoryTest() {

    @Test
    fun `test if getCharacterDetails calls networkClient when isOnline`() {
        runBlocking {
            // GIVEN
            val characterId: Int = DataFactory.randomInt()
            stubConfigProviderIsOnline(isOnline = true)
            stubNetworkClientFetchCharacterDetails(NetworkResponseFactory.CharacterDetails.characterResponse())
            stubCharacterDaoQueryCharacter(CharacterDataFactory.Db.makeCharacter(characterId = characterId))

            // WHEN
            repository.getCharacterDetails(characterId = characterId)

            // THEN
            Verify on networkClient that networkClient.fetchCharacterDetails(characterId = any()) was called
            VerifyNoFurtherInteractions on networkClient

            Verify on characterDao that characterDao.insertOrUpdateCharacter(character = any()) was called
            Verify on characterDao that characterDao.queryCharacter(characterId = any()) was called
            VerifyNoFurtherInteractions on characterDao
        }
    }

    @Test
    fun `test if getCharacterDetails calls db only when isOffline`() {
        runBlocking {
            // GIVEN
            val characterId: Int = DataFactory.randomInt()
            stubConfigProviderIsOnline(isOnline = false)
            stubCharacterDaoQueryCharacter(CharacterDataFactory.Db.makeCharacter(characterId = characterId))

            // WHEN
            repository.getCharacterDetails(characterId = characterId)

            // THEN
            VerifyNoInteractions on networkClient

            Verify on characterDao that characterDao.queryCharacter(characterId = any()) was called
            VerifyNoFurtherInteractions on characterDao
        }
    }

    private suspend fun stubNetworkClientFetchCharacterDetails(character: Response<NetworkCharacterModel>) {
        When calling networkClient.fetchCharacterDetails(characterId = any()) itReturns character
    }

    private suspend fun stubCharacterDaoQueryCharacter(character: DbCharacterModel?) {
        When calling characterDao.queryCharacter(characterId = any()) itReturns character
    }
}
