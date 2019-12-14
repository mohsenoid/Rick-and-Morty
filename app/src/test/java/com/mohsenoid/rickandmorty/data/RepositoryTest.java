package com.mohsenoid.rickandmorty.data;

import com.mohsenoid.rickandmorty.config.ConfigProvider;
import com.mohsenoid.rickandmorty.data.db.Datastore;
import com.mohsenoid.rickandmorty.data.service.ApiClient;
import com.mohsenoid.rickandmorty.executor.TaskExecutor;
import com.mohsenoid.rickandmorty.test.TestTaskExecutor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public abstract class RepositoryTest {

    @Mock
    Datastore datastore;

    @Mock
    ApiClient apiClient;
    Repository repository;
    @Mock
    private ConfigProvider configProvider;
    private TaskExecutor testTaskExecutor = new TestTaskExecutor();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        repository = new RepositoryImpl(datastore, apiClient, testTaskExecutor, configProvider);
    }

    @Test
    public void testIFQueryEpisodesCallsApiClientMethodWhenInOnline() throws Exception {
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
    public void testIFQueryEpisodesCallsApiClientMethodWhenInOffline() throws Exception {
        // GIVEN
        stubConfigProviderIsOnline(false);
        int page = 1;

        // WHEN
        repository.queryEpisodes(page, null);

        // THEN
        verify(datastore, times(1)).queryAllEpisodes(page);
        verify(apiClient, times(0)).getEpisodes(page);
    }

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

    void stubConfigProviderIsOnline(boolean isOnline) {
        when(configProvider.isOnline())
                .thenReturn(isOnline);
    }
}
