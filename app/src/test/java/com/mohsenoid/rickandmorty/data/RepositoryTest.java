package com.mohsenoid.rickandmorty.data;

import com.mohsenoid.rickandmorty.data.db.Db;
import com.mohsenoid.rickandmorty.data.mapper.CharacterMapper;
import com.mohsenoid.rickandmorty.data.mapper.EpisodeMapper;
import com.mohsenoid.rickandmorty.data.mapper.LocationMapper;
import com.mohsenoid.rickandmorty.data.mapper.OriginMapper;
import com.mohsenoid.rickandmorty.data.network.NetworkClient;
import com.mohsenoid.rickandmorty.domain.Repository;
import com.mohsenoid.rickandmorty.test.TestTaskExecutor;
import com.mohsenoid.rickandmorty.util.config.ConfigProvider;
import com.mohsenoid.rickandmorty.util.executor.TaskExecutor;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

abstract class RepositoryTest {

    @Mock
    Db db;

    @Mock
    NetworkClient networkClient;

    Repository repository;

    @Mock
    private ConfigProvider configProvider;

    private TaskExecutor testTaskExecutor = new TestTaskExecutor();
    private EpisodeMapper episodeMapper = EpisodeMapper.getInstance();
    private CharacterMapper characterMapper = CharacterMapper.getInstance(OriginMapper.getInstance(), LocationMapper.getInstance());

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        repository = RepositoryImpl.getInstance(db, networkClient, testTaskExecutor, testTaskExecutor, configProvider, episodeMapper, characterMapper);
    }

    @After
    public void tearDown() {
        RepositoryImpl.instance = null;
    }

    void stubConfigProviderIsOnline(boolean isOnline) {
        when(configProvider.isOnline())
                .thenReturn(isOnline);
    }
}
