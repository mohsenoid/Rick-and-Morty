package com.mohsenoid.rickandmorty.data;

import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RepositoryQueryEpisodesTest extends RepositoryTest {

    @Test
    public void testIfQueryEpisodesCallsApiClientMethodWhenIsOnline() throws IOException, JSONException {
        // GIVEN
        stubConfigProviderIsOnline(true);
        int page = 1;

        // WHEN
        repository.queryEpisodes(page, null);

        // THEN
        verify(db, times(1)).queryAllEpisodes(page);
        verify(networkClient, times(1)).getEpisodes(page);
    }

    @Test
    public void testIfQueryEpisodesCallsApiClientMethodWhenIsOffline() throws IOException, JSONException {
        // GIVEN
        stubConfigProviderIsOnline(false);
        int page = 1;

        // WHEN
        repository.queryEpisodes(page, null);

        // THEN
        verify(db, times(1)).queryAllEpisodes(page);
        verify(networkClient, times(0)).getEpisodes(page);
    }
}
