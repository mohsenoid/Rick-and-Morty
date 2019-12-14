package com.mohsenoid.rickandmorty.data;

import org.junit.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RepositoryQueryEpisodesTest extends RepositoryTest {

    @Test
    public void testIFQueryEpisodesCallsApiClientMethodWhenIsOnline() throws Exception {
        // GIVEN
        stubConfigProviderIsOnline(true);
        int page = 1;

        // WHEN
        repository.queryEpisodes(page, null);

        // THEN
        verify(datastore, times(0)).queryAllEpisodes(page);
        verify(apiClient, times(1)).getEpisodes(page);
    }

    @Test
    public void testIFQueryEpisodesCallsApiClientMethodWhenIsOffline() throws Exception {
        // GIVEN
        stubConfigProviderIsOnline(false);
        int page = 1;

        // WHEN
        repository.queryEpisodes(page, null);

        // THEN
        verify(datastore, times(1)).queryAllEpisodes(page);
        verify(apiClient, times(0)).getEpisodes(page);
    }
}
