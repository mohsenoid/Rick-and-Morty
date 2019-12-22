package com.mohsenoid.rickandmorty.view.character.list

import com.mohsenoid.rickandmorty.data.DataCallback
import com.mohsenoid.rickandmorty.data.mapper.CharacterDbMapper
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.test.CharacterDataFactory
import com.mohsenoid.rickandmorty.test.CharacterDataFactory.Entity.makeEntityCharactersModelList
import com.mohsenoid.rickandmorty.test.DataFactory.randomInt
import com.mohsenoid.rickandmorty.test.DataFactory.randomString
import com.mohsenoid.rickandmorty.util.config.ConfigProvider
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

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
    fun `test if isOnline loadCharacters calls view showLoading`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)

        // WHEN
        presenter.loadCharacters()

        // THEN
        verify(view, times(1)).showLoading()
    }

    @Test
    fun `test if isOffline loadCharacters calls view showOfflineMessage`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(false)

        // WHEN
        presenter.loadCharacters()

        // THEN
        verify(view, times(1)).showOfflineMessage(isCritical = false)
    }

    @Test
    fun `test loadCharacters calls repository queryCharactersByIds`() = runBlockingTest {
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
        verify(repository, times(1))
            .queryCharactersByIds(characterIds = eq<List<Int>>(characterIds), callback = any())
    }

    @Test
    fun `test loadCharacters calls view setCharacters OnSuccess`() = runBlockingTest {
        // GIVEN
        val characters =
            makeEntityCharactersModelList(5)
        stubRepositoryQueryCharactersOnSuccess(characters)

        // WHEN
        presenter.loadCharacters()

        // THEN
        verify(view, times(1)).setCharacters(characters = eq(characters))
    }

    @Test
    fun `test loadCharacters calls view showMessage OnError`() = runBlockingTest {
        // GIVEN
        val errorMessage = randomString()
        stubRepositoryQueryCharactersOnError(Exception(errorMessage))

        // WHEN
        presenter.loadCharacters()

        // THEN
        verify(view, times(1)).showMessage(message = errorMessage)
    }

    @Test
    fun `test loadCharacters calls view hideLoading OnSuccess`() = runBlockingTest {
        // GIVEN
        val characters =
            makeEntityCharactersModelList(5)
        stubRepositoryQueryCharactersOnSuccess(characters)

        // WHEN
        presenter.loadCharacters()

        // THEN
        verify(view, times(1)).hideLoading()
    }

    @Test
    fun `test loadCharacters calls view hideLoading OnError`() = runBlockingTest {
        // GIVEN
        stubRepositoryQueryCharactersOnError(Exception())

        // WHEN
        presenter.loadCharacters()

        // THEN
        verify(view, times(1)).hideLoading()
    }

    @Test
    fun `test killCharacter calls repository killCharacter when character isAlive`() =
        runBlockingTest {
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
            verify(repository, times(1))
                .killCharacter(
                    characterId = eq(characterId),
                    callback = any()
                )
        }

    @Test
    fun `test killCharacter skips calling repository killCharacter when character isNotAlive`() =
        runBlockingTest {
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
            verify(repository, times(0))
                .killCharacter(
                    characterId = eq(character.id),
                    callback = any()
                )
        }

    private fun stubConfigProviderIsOnline(isOnline: Boolean) {
        whenever(configProvider.isOnline())
            .thenReturn(isOnline)
    }

    private fun stubRepositoryQueryCharactersOnSuccess(characters: List<CharacterEntity>) =
        runBlockingTest {
            whenever(repository.queryCharactersByIds(characterIds = any(), callback = any()))
                .then { invocation ->
                    val callback =
                        invocation.getArgument<DataCallback<List<CharacterEntity>>>(
                            1
                        )
                    callback.onSuccess(characters)
                    null
                }
        }

    private fun stubRepositoryQueryCharactersOnError(exception: Exception) = runBlockingTest {
        whenever(repository.queryCharactersByIds(characterIds = any(), callback = any()))
            .then { invocation ->
                val callback =
                    invocation.getArgument<DataCallback<List<CharacterEntity>>>(
                        1
                    )
                callback.onError(exception)
                null
            }
    }
}
