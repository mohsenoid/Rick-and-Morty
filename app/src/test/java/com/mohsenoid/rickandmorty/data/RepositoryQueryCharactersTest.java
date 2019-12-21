package com.mohsenoid.rickandmorty.data;

import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RepositoryQueryCharactersTest extends RepositoryTest {

    @Test
    public void testIfQueryCharactersCallsApiClientMethodWhenIsOnline() throws IOException, JSONException {
        // GIVEN
        stubConfigProviderIsOnline(true);

        // WHEN
        repository.queryCharactersByIds(null, null);

        // THEN
        verify(db, times(1)).queryCharactersByIds(null);
        verify(networkClient, times(1)).getCharactersByIds(null);
    }

    @Test
    public void testIfQueryCharactersCallsApiClientMethodWhenIsOffline() throws IOException, JSONException {
        // GIVEN
        stubConfigProviderIsOnline(false);

        // WHEN
        repository.queryCharactersByIds(null, null);

        // THEN
        verify(db, times(1)).queryCharactersByIds(null);
        verify(networkClient, times(0)).getCharactersByIds(null);
    }
}
