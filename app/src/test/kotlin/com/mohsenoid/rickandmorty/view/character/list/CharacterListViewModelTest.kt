package com.mohsenoid.rickandmorty.view.character.list

import com.mohsenoid.rickandmorty.data.exception.NoOfflineDataException
import com.mohsenoid.rickandmorty.data.mapper.CharacterDbMapper
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.test.CharacterDataFactory
import com.mohsenoid.rickandmorty.test.DataFactory
import com.mohsenoid.rickandmorty.util.config.ConfigProvider
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.Verify
import org.amshove.kluent.VerifyNoFurtherInteractions
import org.amshove.kluent.VerifyNoInteractions
import org.amshove.kluent.When
import org.amshove.kluent.called
import org.amshove.kluent.calling
import org.amshove.kluent.itAnswers
import org.amshove.kluent.itReturns
import org.amshove.kluent.on
import org.amshove.kluent.that
import org.amshove.kluent.was
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CharacterListViewModelTest {

    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var configProvider: ConfigProvider

    @Mock
    lateinit var view: CharacterListContract.View

    private lateinit var presenter: CharacterListContract.Presenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = CharacterListViewModel(repository = repository, configProvider = configProvider)
        presenter.bind(view)
    }

    @Test
    fun `test if loadCharacters calls view showLoading`() {
        runBlocking {
            // GIVEN

            // WHEN
            presenter.loadCharacters()

            // THEN
            Verify on view that view.showLoading() was called
        }
    }

    @Test
    fun `test if isOffline loadCharacters calls view showOfflineMessage`() {
        runBlocking {
            // GIVEN
            stubConfigProviderIsOnline(false)

            // WHEN
            presenter.loadCharacters()

            // THEN
            Verify on view that view.showOfflineMessage(isCritical = false) was called
        }
    }

    @Test
    fun `test loadCharacters calls repository queryCharactersByIds`() {
        runBlocking {
            // GIVEN
            val characterIds: List<Int> = listOf(
                DataFactory.randomInt(),
                DataFactory.randomInt(),
                DataFactory.randomInt()
            )

            presenter.characterIds = characterIds

            // WHEN
            presenter.loadCharacters()

            // THEN
            Verify on repository that repository.getCharactersByIds(characterIds = characterIds) was called
        }
    }

    @Test
    fun `test loadCharacters calls view setCharacters OnSuccess`() {
        runBlocking {
            // GIVEN
            val characters: List<CharacterEntity> =
                CharacterDataFactory.Entity.makeCharacters(count = 5)
            stubRepositoryGetCharactersByIdsOnSuccess(characters)

            // WHEN
            presenter.loadCharacters()

            // THEN
            Verify on view that view.setCharacters(characters = characters) was called
        }
    }

    @Test
    fun `test loadCharacters calls view showMessage OnError`() {
        runBlocking {
            // GIVEN
            val errorMessage = DataFactory.randomString()
            stubRepositoryGetCharactersByIdsOnError(Exception(errorMessage))

            // WHEN
            presenter.loadCharacters()

            // THEN
            Verify on view that view.showMessage(message = errorMessage) was called
        }
    }

    @Test
    fun `test loadCharacters calls view onNoOfflineData having NoOfflineDataException OnError`() {
        runBlocking {
            // GIVEN
            stubRepositoryGetCharactersByIdsOnError(NoOfflineDataException())

            // WHEN
            presenter.loadCharacters()

            // THEN
            Verify on view that view.onNoOfflineData() was called
        }
    }

    @Test
    fun `test loadCharacters calls view hideLoading OnSuccess`() {
        runBlocking {
            // GIVEN
            val characters: List<CharacterEntity> =
                CharacterDataFactory.Entity.makeCharacters(count = 5)
            stubRepositoryGetCharactersByIdsOnSuccess(characters)

            // WHEN
            presenter.loadCharacters()

            // THEN
            Verify on view that view.hideLoading() was called
        }
    }

    @Test
    fun `test loadCharacters calls view hideLoading OnError`() {
        runBlocking {
            // GIVEN
            stubRepositoryGetCharactersByIdsOnError(Exception())

            // WHEN
            presenter.loadCharacters()

            // THEN
            Verify on view that view.hideLoading() was called
        }
    }

    @Test
    fun `test killCharacter calls repository killCharacter when character isAlive`() {
        runBlocking {
            // GIVEN
            val characterId = DataFactory.randomInt()
            val character: CharacterEntity = CharacterDataFactory.Entity.makeCharacter(
                characterId = characterId,
                status = CharacterDbMapper.ALIVE,
                isAlive = true,
                isKilledByUser = false
            )

            // WHEN
            presenter.killCharacter(character)

            // THEN
            Verify on repository that repository.killCharacter(
                characterId = characterId
            ) was called
            VerifyNoFurtherInteractions on repository
        }
    }

    @Test
    fun `test killCharacter skips calling repository killCharacter when character isNotAlive`() {
        runBlocking {
            // GIVEN
            val characterId = DataFactory.randomInt()
            val character: CharacterEntity = CharacterDataFactory.Entity.makeCharacter(
                characterId = characterId,
                status = CharacterDbMapper.ALIVE,
                isAlive = true,
                isKilledByUser = true
            )

            // WHEN
            presenter.killCharacter(character)

            // THEN
            VerifyNoInteractions on repository
        }
    }

    private fun stubConfigProviderIsOnline(isOnline: Boolean) {
        When calling configProvider.isOnline() itReturns isOnline
    }

    private suspend fun stubRepositoryGetCharactersByIdsOnSuccess(characters: List<CharacterEntity>) {
        When calling repository.getCharactersByIds(any()) itReturns characters
    }

    private suspend fun stubRepositoryGetCharactersByIdsOnError(exception: Exception) {
        When calling repository.getCharactersByIds(any()) itAnswers { throw exception }
    }
}
