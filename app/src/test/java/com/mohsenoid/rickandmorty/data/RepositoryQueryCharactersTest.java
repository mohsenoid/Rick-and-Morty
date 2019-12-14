package com.mohsenoid.rickandmorty.data;

import org.junit.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RepositoryQueryCharactersTest extends RepositoryTest {

    @Test
    public void testIFQueryCharactersCallsApiClientMethodWhenInOnline() throws Exception {
        // GIVEN
        stubConfigProviderIsOnline(true);
        int page = 1;

        // WHEN
        repository.queryCharacters(page, null);

        // THEN
        verify(datastore, times(0)).queryAllCharacters(page);
        verify(apiClient, times(1)).getCharacters(page);
    }

    @Test
    public void testIFQueryCharactersCallsApiClientMethodWhenInOffline() throws Exception {
        // GIVEN
        stubConfigProviderIsOnline(false);
        int page = 1;

        // WHEN
        repository.queryCharacters(page, null);

        // THEN
        verify(datastore, times(1)).queryAllCharacters(page);
        verify(apiClient, times(0)).getCharacters(page);
    }
}
