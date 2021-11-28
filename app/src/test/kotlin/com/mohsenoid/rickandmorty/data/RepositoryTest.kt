package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.api.ApiClient
import com.mohsenoid.rickandmorty.data.api.model.ApiCharacter
import com.mohsenoid.rickandmorty.data.api.model.ApiEpisode
import com.mohsenoid.rickandmorty.data.api.model.ApiLocation
import com.mohsenoid.rickandmorty.data.api.model.ApiOrigin
import com.mohsenoid.rickandmorty.data.db.dao.DbCharacterDao
import com.mohsenoid.rickandmorty.data.db.dao.DbEpisodeDao
import com.mohsenoid.rickandmorty.data.db.model.DbCharacter
import com.mohsenoid.rickandmorty.data.db.model.DbEpisode
import com.mohsenoid.rickandmorty.data.db.model.DbLocation
import com.mohsenoid.rickandmorty.data.db.model.DbOrigin
import com.mohsenoid.rickandmorty.data.mapper.Mapper
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.model.ModelCharacter
import com.mohsenoid.rickandmorty.domain.model.ModelEpisode
import com.mohsenoid.rickandmorty.domain.model.ModelLocation
import com.mohsenoid.rickandmorty.domain.model.ModelOrigin
import com.mohsenoid.rickandmorty.test.TestDispatcherProvider
import com.mohsenoid.rickandmorty.util.StatusProvider
import com.mohsenoid.rickandmorty.util.dispatcher.DispatcherProvider
import org.amshove.kluent.When
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

abstract class RepositoryTest {

    @Mock
    lateinit var characterDao: DbCharacterDao

    @Mock
    lateinit var episodeDao: DbEpisodeDao

    @Mock
    lateinit var networkClient: ApiClient

    @Mock
    private lateinit var statusProvider: StatusProvider

    lateinit var repository: Repository

    private val testDispatcherProvider: DispatcherProvider = TestDispatcherProvider()

    private val episodeDbEntityMapper: Mapper<ApiEpisode, DbEpisode> =
        EpisodeDbMapper()
    private val entityEpisodeEntityMapper: Mapper<DbEpisode, ModelEpisode> =
        EpisodeEntityMapper()
    private val originDbMapperOrigin: Mapper<ApiOrigin, DbOrigin> =
        OriginDbMapper()
    private val locationDbMapperLocation: Mapper<ApiLocation, DbLocation> =
        LocationDbMapper()
    private val characterDbMapperCharacter: Mapper<ApiCharacter, DbCharacter> =
        CharacterDbMapper(originDbMapperOrigin, locationDbMapperLocation)
    private val entityOriginMapper: Mapper<DbOrigin, ModelOrigin> =
        OriginEntityMapper()
    private val entityLocationMapper: Mapper<DbLocation, ModelLocation> =
        LocationEntityMapper()
    private val entityCharacterMapper: Mapper<DbCharacter, ModelCharacter> =
        CharacterEntityMapper(entityOriginMapper, entityLocationMapper)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        repository = RepositoryImpl(
            characterDao = characterDao,
            episodeDao = episodeDao,
            networkClient = networkClient,
            ioDispatcher = testDispatcherProvider.ioDispatcher,
            configProvider = statusProvider,
            episodeDbMapper = episodeDbEntityMapper,
            episodeEntityMapper = entityEpisodeEntityMapper,
            characterDbMapper = characterDbMapperCharacter,
            characterEntityMapper = entityCharacterMapper
        )
    }

    fun stubConfigProviderIsOnline(isOnline: Boolean) {
        When calling statusProvider.isOnline() itReturns isOnline
    }
}
