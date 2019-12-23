package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.test.DataFactory
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RepositoryQueryCharactersTest : RepositoryTest() {

    @Test
    fun `test if queryCharactersByIds calls networkClient when isOnline`() {
        runBlocking {
            // GIVEN
            stubConfigProviderIsOnline(true)
            val characterIds = DataFactory.randomIntList(5)

            // WHEN
            repository.queryCharactersByIds(characterIds, null)

            // THEN
            verify(characterDao, times(1)).queryCharactersByIds(characterIds)
            verify(networkClient, times(1)).getCharactersByIds(characterIds)
        }
    }

    @Test
    fun `test if queryCharactersByIds calls db only when isOffline`() {
        runBlocking {
            // GIVEN
            stubConfigProviderIsOnline(false)
            val characterIds = DataFactory.randomIntList(5)

            // WHEN
            repository.queryCharactersByIds(characterIds, null)

            // THEN
            verify(characterDao, times(1)).queryCharactersByIds(characterIds)
            verify(networkClient, times(0)).getCharactersByIds(characterIds)
        }
    }
}
