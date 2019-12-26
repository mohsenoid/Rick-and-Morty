package com.mohsenoid.rickandmorty.view.character.list

import com.mohsenoid.rickandmorty.data.mapper.CharacterDbMapper
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.test.CharacterDataFactory
import com.mohsenoid.rickandmorty.test.CharacterDataFactory.Entity.makeEntityCharactersModelList
import com.mohsenoid.rickandmorty.test.DataFactory.randomInt
import com.mohsenoid.rickandmorty.test.DataFactory.randomString
import com.mohsenoid.rickandmorty.util.config.ConfigProvider
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
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
import java.util.ArrayList

class CharacterListPresenterTest {

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
        presenter = CharacterListPresenter(repository = repository, configProvider = configProvider)
        presenter.bind(view)
    }

    @Test
    fun `test if isOnline loadCharacters calls view showLoading`() {
        runBlocking {
            // GIVEN
            stubConfigProviderIsOnline(true)

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
            stubConfigProviderIsOnline(true)
            val characterIds: MutableList<Int> = ArrayList()
            characterIds.add(randomInt())
            characterIds.add(randomInt())
            characterIds.add(randomInt())
            presenter.characterIds = characterIds

            // WHEN
            presenter.loadCharacters()

            // THEN
            Verify on repository that repository.getCharactersByIds(
                characterIds = eq<List<Int>>(
                    characterIds
                )
            ) was called
        }
    }

    @Test
    fun `test loadCharacters calls view setCharacters OnSuccess`() {
        runBlocking {
            // GIVEN
            val characters =
                makeEntityCharactersModelList(5)
            stubRepositoryQueryCharactersOnSuccess(characters)

            // WHEN
            presenter.loadCharacters()

            // THEN
            Verify on view that view.setCharacters(characters = eq(characters)) was called
        }
    }

    @Test
    fun `test loadCharacters calls view showMessage OnError`() {
        runBlocking {
            // GIVEN
            val errorMessage = randomString()
            stubRepositoryQueryCharactersOnError(Exception(errorMessage))

            // WHEN
            presenter.loadCharacters()

            // THEN
            Verify on view that view.showMessage(message = errorMessage) was called
        }
    }

    @Test
    fun `test loadCharacters calls view hideLoading OnSuccess`() {
        runBlocking {
            // GIVEN
            val characters =
                makeEntityCharactersModelList(5)
            stubRepositoryQueryCharactersOnSuccess(characters)

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
            stubRepositoryQueryCharactersOnError(Exception())

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
            stubConfigProviderIsOnline(true)
            val characterId = randomInt()
            val character: CharacterEntity =
                CharacterDataFactory.Entity.makeCharacterEntity(
                    characterId = characterId,
                    status = CharacterDbMapper.ALIVE,
                    isAlive = true,
                    isKilledByUser = false
                )

            // WHEN
            presenter.killCharacter(character)

            // THEN
            Verify on repository that repository.killCharacter(
                characterId = eq(characterId)
            ) was called
            VerifyNoFurtherInteractions on repository
        }
    }

    @Test
    fun `test killCharacter skips calling repository killCharacter when character isNotAlive`() {
        runBlocking {
            // GIVEN
            stubConfigProviderIsOnline(true)
            val characterId = randomInt()
            val character: CharacterEntity =
                CharacterDataFactory.Entity.makeCharacterEntity(
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

    private suspend fun stubRepositoryQueryCharactersOnSuccess(characters: List<CharacterEntity>) {
        When calling repository.getCharactersByIds(any()) itReturns characters
    }

    private suspend fun stubRepositoryQueryCharactersOnError(exception: Exception) {
        When calling repository.getCharactersByIds(any()) itAnswers { throw exception }
    }
}
