package com.mohsenoid.rickandmorty.data;

import org.junit.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RepositoryQueryCharactersTest extends RepositoryTest {

    @Test
    public void testIFQueryCharactersCallsApiClientMethodWhenIsOnline() throws Exception {
        // GIVEN
        stubConfigProviderIsOnline(true);

        // WHEN
        repository.queryCharacters(null, null);

        // THEN
        verify(datastore, times(0)).queryAllCharacters(null);
        verify(apiClient, times(1)).getCharacters(null);
    }

    @Test
    public void testIFQueryCharactersCallsApiClientMethodWhenIsOffline() throws Exception {
        // GIVEN
        stubConfigProviderIsOnline(false);

        // WHEN
        repository.queryCharacters(null, null);

        // THEN
        verify(datastore, times(1)).queryAllCharacters(null);
        verify(apiClient, times(0)).getCharacters(null);
    }
}
