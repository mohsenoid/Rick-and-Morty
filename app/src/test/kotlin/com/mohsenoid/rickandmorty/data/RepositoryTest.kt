package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.db.Db
import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel
import com.mohsenoid.rickandmorty.data.mapper.*
import com.mohsenoid.rickandmorty.data.network.NetworkClient
import com.mohsenoid.rickandmorty.data.network.dto.NetworkCharacterModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkEpisodeModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkLocationModel
import com.mohsenoid.rickandmorty.data.network.dto.NetworkOriginModel
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.domain.entity.LocationEntity
import com.mohsenoid.rickandmorty.domain.entity.OriginEntity
import com.mohsenoid.rickandmorty.test.TestTaskExecutor
import com.mohsenoid.rickandmorty.util.config.ConfigProvider
import com.mohsenoid.rickandmorty.util.executor.TaskExecutor
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

abstract class RepositoryTest {

    @Mock
    lateinit var db: Db

    @Mock
    lateinit var networkClient: NetworkClient

    @Mock
    private lateinit var configProvider: ConfigProvider

    lateinit var repository: Repository

    private val testTaskExecutor: TaskExecutor = TestTaskExecutor()

    private val episodeDbMapper: Mapper<NetworkEpisodeModel, DbEpisodeModel> =
        EpisodeDbMapper()
    private val episodeEntityMapper: Mapper<DbEpisodeModel, EpisodeEntity> =
        EpisodeEntityMapper()
    private val originDbMapper: Mapper<NetworkOriginModel, DbOriginModel> =
        OriginDbMapper()
    private val locationDbMapper: Mapper<NetworkLocationModel, DbLocationModel> =
        LocationDbMapper()
    private val characterDbMapper: Mapper<NetworkCharacterModel, DbCharacterModel> =
        CharacterDbMapper(originDbMapper, locationDbMapper)
    private val originEntityMapper: Mapper<DbOriginModel, OriginEntity> =
        OriginEntityMapper()
    private val locationEntityMapper: Mapper<DbLocationModel, LocationEntity> =
        LocationEntityMapper()
    private val characterEntityMapper: Mapper<DbCharacterModel, CharacterEntity> =
        CharacterEntityMapper(originEntityMapper, locationEntityMapper)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        repository = RepositoryImpl(
            db = db,
            networkClient = networkClient,
            ioTaskExecutor = testTaskExecutor,
            mainTaskExecutor = testTaskExecutor,
            configProvider = configProvider,
            episodeDbMapper = episodeDbMapper,
            episodeEntityMapper = episodeEntityMapper,
            characterDbMapper = characterDbMapper,
            characterEntityMapper = characterEntityMapper
        )
    }

    fun stubConfigProviderIsOnline(isOnline: Boolean) {
        whenever(configProvider.isOnline())
            .thenReturn(isOnline)
    }
}
