package com.mohsenoid.rickandmorty.data;

import org.junit.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RepositoryQueryCharacterDetailsTest extends RepositoryTest {

    @Test
    public void testIFQueryCharacterDetailsCallsApiClientMethodWhenInOnline() throws Exception {
        // GIVEN
        stubConfigProviderIsOnline(true);
        int page = 1;

        // WHEN
        repository.queryCharacterDetails(page, null);

        // THEN
        verify(datastore, times(0)).queryCharacter(page);
        verify(apiClient, times(1)).getCharacterDetails(page);
    }

    @Test
    public void testIFQueryCharacterDetailsCallsApiClientMethodWhenInOffline() throws Exception {
        // GIVEN
        stubConfigProviderIsOnline(false);
        int page = 1;

        // WHEN
        repository.queryCharacterDetails(page, null);

        // THEN
        verify(datastore, times(1)).queryCharacter(page);
        verify(apiClient, times(0)).getCharacterDetails(page);
    }
}
