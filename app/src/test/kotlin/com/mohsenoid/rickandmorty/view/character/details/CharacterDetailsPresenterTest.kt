package com.mohsenoid.rickandmorty.view.character.details

import com.mohsenoid.rickandmorty.data.exception.NoOfflineDataException
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.test.CharacterDataFactory
import com.mohsenoid.rickandmorty.test.DataFactory
import com.mohsenoid.rickandmorty.util.config.ConfigProvider
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.Verify
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

class CharacterDetailsPresenterTest {

    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var configProvider: ConfigProvider

    @Mock
    lateinit var view: CharacterDetailsContract.View

    private lateinit var presenter: CharacterDetailsContract.Presenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter =
            CharacterDetailsPresenter(repository = repository, configProvider = configProvider)
        presenter.bind(view)
    }

    @Test
    fun `test if loadCharacter calls view showLoading`() {
        runBlocking {
            // GIVEN

            // WHEN
            presenter.loadCharacter(DataFactory.randomInt())

            // THEN
            Verify on view that view.showLoading() was called
        }
    }

    @Test
    fun `test if isOffline loadCharacter calls view showOfflineMessage`() {
        runBlocking {
            // GIVEN
            stubConfigProviderIsOnline(false)

            // WHEN
            presenter.loadCharacter(DataFactory.randomInt())

            // THEN
            Verify on view that view.showOfflineMessage(isCritical = false) was called
        }
    }

    @Test
    fun `test loadCharacter calls repository getCharacterDetails`() {
        runBlocking {
            // GIVEN
            val characterId: Int = DataFactory.randomInt()

            // WHEN
            presenter.loadCharacter(characterId)

            // THEN
            Verify on repository that repository.getCharacterDetails(characterId = characterId) was called
        }
    }

    @Test
    fun `test loadCharacter calls view setCharacter OnSuccess`() {
        runBlocking {
            // GIVEN
            val character: CharacterEntity = CharacterDataFactory.Entity.makeCharacter()
            stubRepositoryGetCharacterDetailsOnSuccess(character)

            // WHEN
            presenter.loadCharacter(DataFactory.randomInt())

            // THEN
            Verify on view that view.setCharacter(character = character) was called
        }
    }

    @Test
    fun `test loadCharacter calls view showMessage OnError`() {
        runBlocking {
            // GIVEN
            val errorMessage = DataFactory.randomString()
            stubRepositoryGetCharacterDetailsOnError(Exception(errorMessage))

            // WHEN
            presenter.loadCharacter(DataFactory.randomInt())

            // THEN
            Verify on view that view.showMessage(message = errorMessage) was called
        }
    }

    @Test
    fun `test loadCharacter calls view onNoOfflineData having NoOfflineDataException OnError`() {
        runBlocking {
            // GIVEN
            stubRepositoryGetCharacterDetailsOnError(NoOfflineDataException())

            // WHEN
            presenter.loadCharacter(DataFactory.randomInt())

            // THEN
            Verify on view that view.onNoOfflineData() was called
        }
    }

    @Test
    fun `test loadCharacter calls view hideLoading OnSuccess`() {
        runBlocking {
            // GIVEN
            val character: CharacterEntity = CharacterDataFactory.Entity.makeCharacter()
            stubRepositoryGetCharacterDetailsOnSuccess(character)

            // WHEN
            presenter.loadCharacter(DataFactory.randomInt())

            // THEN
            Verify on view that view.hideLoading() was called
        }
    }

    @Test
    fun `test loadCharacter calls view hideLoading OnError`() {
        runBlocking {
            // GIVEN
            stubRepositoryGetCharacterDetailsOnError(Exception())

            // WHEN
            presenter.loadCharacter(DataFactory.randomInt())

            // THEN
            Verify on view that view.hideLoading() was called
        }
    }

    private fun stubConfigProviderIsOnline(isOnline: Boolean) {
        When calling configProvider.isOnline() itReturns isOnline
    }

    private suspend fun stubRepositoryGetCharacterDetailsOnSuccess(character: CharacterEntity) {
        When calling repository.getCharacterDetails(any()) itReturns character
    }

    private suspend fun stubRepositoryGetCharacterDetailsOnError(exception: Exception) {
        When calling repository.getCharacterDetails(any()) itAnswers { throw exception }
    }
}
