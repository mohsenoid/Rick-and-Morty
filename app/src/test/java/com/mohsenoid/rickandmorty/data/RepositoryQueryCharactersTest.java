package com.mohsenoid.rickandmorty.data;

import org.junit.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RepositoryQueryCharactersTest extends RepositoryTest {

    @Test
    public void testIfQueryCharactersCallsApiClientMethodWhenIsOnline() throws Exception {
        // GIVEN
        stubConfigProviderIsOnline(true);

        // WHEN
        repository.queryCharacters(null, null);

        // THEN
        verify(datastore, times(1)).queryAllCharacters(null);
        verify(apiClient, times(1)).getCharacters(null);
    }

    @Test
    public void testIfQueryCharactersCallsApiClientMethodWhenIsOffline() throws Exception {
        // GIVEN
        stubConfigProviderIsOnline(false);

        // WHEN
        repository.queryCharacters(null, null);

        // THEN
        verify(datastore, times(1)).queryAllCharacters(null);
        verify(apiClient, times(0)).getCharacters(null);
    }
}
