package com.mohsenoid.rickandmorty.data;

import com.mohsenoid.rickandmorty.config.ConfigProvider;
import com.mohsenoid.rickandmorty.data.db.Datastore;
import com.mohsenoid.rickandmorty.data.service.ApiClient;
import com.mohsenoid.rickandmorty.executor.TaskExecutor;
import com.mohsenoid.rickandmorty.test.TestTaskExecutor;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

        repository = new RepositoryImpl(datastore, apiClient, testTaskExecutor, testTaskExecutor, configProvider);
    }

    void stubConfigProviderIsOnline(boolean isOnline) {
        when(configProvider.isOnline())
                .thenReturn(isOnline);
    }
}
