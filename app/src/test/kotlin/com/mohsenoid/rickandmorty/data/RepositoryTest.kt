package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.db.DbCharacterDao
import com.mohsenoid.rickandmorty.data.db.DbEpisodeDao
import com.mohsenoid.rickandmorty.data.db.dto.DbCharacterModel
import com.mohsenoid.rickandmorty.data.db.dto.DbEpisodeModel
import com.mohsenoid.rickandmorty.data.db.dto.DbLocationModel
import com.mohsenoid.rickandmorty.data.db.dto.DbOriginModel
import com.mohsenoid.rickandmorty.data.mapper.CharacterDbMapper
import com.mohsenoid.rickandmorty.data.mapper.CharacterEntityMapper
import com.mohsenoid.rickandmorty.data.mapper.EpisodeDbMapper
import com.mohsenoid.rickandmorty.data.mapper.EpisodeEntityMapper
import com.mohsenoid.rickandmorty.data.mapper.LocationDbMapper
import com.mohsenoid.rickandmorty.data.mapper.LocationEntityMapper
import com.mohsenoid.rickandmorty.data.mapper.Mapper
import com.mohsenoid.rickandmorty.data.mapper.OriginDbMapper
import com.mohsenoid.rickandmorty.data.mapper.OriginEntityMapper
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
import com.mohsenoid.rickandmorty.test.TestDispatcherProvider
import com.mohsenoid.rickandmorty.util.config.ConfigProvider
import com.mohsenoid.rickandmorty.util.dispatcher.DispatcherProvider
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

abstract class RepositoryTest {

    @Mock
    lateinit var characterDao: DbCharacterDao

    @Mock
    lateinit var episodeDao: DbEpisodeDao

    @Mock
    lateinit var networkClient: NetworkClient

    @Mock
    private lateinit var configProvider: ConfigProvider

    lateinit var repository: Repository

    private val testDispatcherProvider: DispatcherProvider = TestDispatcherProvider()

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
            characterDao = characterDao,
            episodeDao = episodeDao,
            networkClient = networkClient,
            dispatcherProvider = testDispatcherProvider,
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
