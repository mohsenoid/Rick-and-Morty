package com.mohsenoid.rickandmorty.data

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class RepositoryQueryEpisodesTest : RepositoryTest() {

    @Test
    fun `test if queryEpisodes calls networkClient and db when isOnline`() {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val page = 1

        // WHEN
        repository.queryEpisodes(page = page, callback = null)

        // THEN
        verify(db, times(1)).queryAllEpisodes(page)
        verify(networkClient, times(1)).getEpisodes(page)
    }

    @Test
    fun `test if queryEpisodes calls db only when isOffline`() {
        // GIVEN
        stubConfigProviderIsOnline(false)
        val page = 1

        // WHEN
        repository.queryEpisodes(page, null)

        // THEN
        verify(db, times(1)).queryAllEpisodes(page)
        verify(networkClient, times(0)).getEpisodes(page)
    }
}
