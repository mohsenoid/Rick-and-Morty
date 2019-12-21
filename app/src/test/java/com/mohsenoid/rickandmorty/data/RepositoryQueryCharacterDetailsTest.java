package com.mohsenoid.rickandmorty.data;

import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RepositoryQueryCharacterDetailsTest extends RepositoryTest {

    @Test
    public void testIfQueryCharacterDetailsCallsApiClientMethodWhenIsOnline() throws IOException, JSONException {
        // GIVEN
        stubConfigProviderIsOnline(true);
        int page = 1;

        // WHEN
        repository.queryCharacterDetails(page, null);

        // THEN
        verify(db, times(1)).queryCharacter(page);
        verify(networkClient, times(1)).getCharacterDetails(page);
    }

    @Test
    public void testIfQueryCharacterDetailsCallsApiClientMethodWhenIsOffline() throws IOException, JSONException {
        // GIVEN
        stubConfigProviderIsOnline(false);
        int page = 1;

        // WHEN
        repository.queryCharacterDetails(page, null);

        // THEN
        verify(db, times(1)).queryCharacter(page);
        verify(networkClient, times(0)).getCharacterDetails(page);
    }
}
