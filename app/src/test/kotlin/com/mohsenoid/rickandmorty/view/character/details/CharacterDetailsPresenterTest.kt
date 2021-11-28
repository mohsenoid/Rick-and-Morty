package com.mohsenoid.rickandmorty.view.character.details

import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.model.ModelCharacter
import com.mohsenoid.rickandmorty.domain.model.QueryResult
import com.mohsenoid.rickandmorty.test.CharacterDataFactory
import com.mohsenoid.rickandmorty.test.DataFactory
import com.mohsenoid.rickandmorty.util.StatusProvider
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailsPresenterTest {

    @MockK
    private lateinit var repository: Repository

    @MockK
    private lateinit var statusProvider: StatusProvider

    private val view: CharacterDetailsContract.View = mockk {
        every { showLoading() } just runs
        every { hideLoading() } just runs
        every { showMessage(any()) } just runs
        every { showOfflineMessage(any()) } just runs
        every { onNoOfflineData() } just runs
        every { setCharacter(any()) } just runs
    }

    private lateinit var presenter: CharacterDetailsContract.Presenter

    private fun stubConfigProviderIsOnline(isOnline: Boolean) {
        every { statusProvider.isOnline() } returns isOnline
    }

    private fun stubRepositoryGetCharacterDetailsOnSuccess(character: ModelCharacter) {
        coEvery { repository.getCharacterDetails(characterId = character.id) } returns
            QueryResult.Successful(character)
    }

    private fun stubRepositoryGetCharacterDetailsOnNoCache(characterId: Int) {
        coEvery { repository.getCharacterDetails(characterId = characterId) } returns QueryResult.NoCache
    }

    private fun stubRepositoryGetCharacterDetailsOnError(characterId: Int) {
        coEvery { repository.getCharacterDetails(characterId = characterId) } returns QueryResult.Error
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        presenter =
            CharacterDetailsPresenter(repository = repository, statusProvider = statusProvider)
        presenter.bind(view)
    }

    @Test
    fun `loading character details shows loading`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val character: ModelCharacter = CharacterDataFactory.makeCharacter()
        stubRepositoryGetCharacterDetailsOnSuccess(character = character)

        // WHEN
        presenter.loadCharacter(characterId = character.id)

        // THEN
        verify(exactly = 1) { view.showLoading() }
    }

    @Test
    fun `loading character details if no network connection shows non-critical offline message`() =
        runBlockingTest {
            // GIVEN
            stubConfigProviderIsOnline(false)
            val character: ModelCharacter = CharacterDataFactory.makeCharacter()
            stubRepositoryGetCharacterDetailsOnSuccess(character = character)

            // WHEN
            presenter.loadCharacter(characterId = character.id)

            // THEN
            verify(exactly = 1) { view.showOfflineMessage(isCritical = false) }
        }

    @Test
    fun `loading character details if no network connection hides loading`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(false)
        val character = CharacterDataFactory.makeCharacter()
        stubRepositoryGetCharacterDetailsOnSuccess(character)

        // WHEN
        presenter.loadCharacter(characterId = character.id)

        // THEN
        verify(exactly = 1) { view.hideLoading() }
    }

    @Test
    fun `loading character details successfully fetches correct result`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val character: ModelCharacter = CharacterDataFactory.makeCharacter()
        stubRepositoryGetCharacterDetailsOnSuccess(character = character)

        // WHEN
        presenter.loadCharacter(characterId = character.id)

        // THEN
        verify(exactly = 1) { view.setCharacter(character = character) }
    }

    @Test
    fun `loading character details successfully hides loading`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val character: ModelCharacter = CharacterDataFactory.makeCharacter()
        stubRepositoryGetCharacterDetailsOnSuccess(character = character)

        // WHEN
        presenter.loadCharacter(characterId = character.id)

        // THEN
        verify(exactly = 1) { view.hideLoading() }
    }

    @Test
    fun `loading character details on error shows error message`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val characterId = DataFactory.randomInt()
        stubRepositoryGetCharacterDetailsOnError(characterId = characterId)

        // WHEN
        presenter.loadCharacter(characterId = characterId)

        // THEN
        verify(exactly = 1) { view.showMessage(any()) }
    }

    @Test
    fun `loading character details on error hides loading`() =
        runBlockingTest {
            // GIVEN
            stubConfigProviderIsOnline(true)
            val characterId = DataFactory.randomInt()
            stubRepositoryGetCharacterDetailsOnError(characterId = characterId)

            // WHEN
            presenter.loadCharacter(characterId = characterId)

            // THEN
            verify(exactly = 1) { view.hideLoading() }
        }

    @Test
    fun `loading character details on no cache shows no offline error`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val characterId = DataFactory.randomInt()
        stubRepositoryGetCharacterDetailsOnNoCache(characterId = characterId)

        // WHEN
        presenter.loadCharacter(characterId = characterId)

        // THEN
        verify(exactly = 1) { view.onNoOfflineData() }
    }

    @Test
    fun `loading character details on no cache hides loading`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val characterId = DataFactory.randomInt()
        stubRepositoryGetCharacterDetailsOnNoCache(characterId = characterId)

        // WHEN
        presenter.loadCharacter(characterId)

        // THEN
        verify(exactly = 1) { view.hideLoading() }
    }
}
