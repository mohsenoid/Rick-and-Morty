package com.mohsenoid.rickandmorty.data

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class RepositoryQueryCharacterDetailsTest : RepositoryTest() {

    @Test
    fun `test if queryCharacterDetails calls networkClient when isOnline`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val page = 1

        // WHEN
        repository.queryCharacterDetails(page, null)

        // THEN
        verify(db, times(1)).queryCharacter(page)
        verify(networkClient, times(1)).getCharacterDetails(page)
    }

    @Test
    fun `test if queryCharacterDetails calls db only when isOffline`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(false)
        val page = 1

        // WHEN
        repository.queryCharacterDetails(page, null)

        // THEN
        verify(db, times(1)).queryCharacter(page)
        verify(networkClient, times(0)).getCharacterDetails(page)
    }
}