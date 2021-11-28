package com.mohsenoid.rickandmorty.view.character.list

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
class CharacterListPresenterTest {

    @MockK
    private lateinit var repository: Repository

    @MockK
    private lateinit var statusProvider: StatusProvider

    private val view: CharacterListContract.View = mockk {
        every { showLoading() } just runs
        every { hideLoading() } just runs
        every { showMessage(any()) } just runs
        every { showOfflineMessage(any()) } just runs
        every { onNoOfflineData() } just runs
        every { setCharacters(any()) } just runs
        every { updateCharacter(any()) } just runs
    }

    private lateinit var presenter: CharacterListContract.Presenter

    private fun stubConfigProviderIsOnline(isOnline: Boolean) {
        every { statusProvider.isOnline() } returns isOnline
    }

    private fun stubRepositoryGetCharactersByIdsOnSuccess(
        characterIds: List<Int>,
        characters: List<ModelCharacter>,
    ) {
        coEvery { repository.getCharactersByIds(characterIds = characterIds) } returns
            QueryResult.Successful(characters)
    }

    private fun stubRepositoryGetCharactersByIdsOnNoCache(characterIds: List<Int>) {
        coEvery { repository.getCharactersByIds(characterIds = characterIds) } returns
            QueryResult.NoCache
    }

    private fun stubRepositoryGetCharactersByIdsOnError(characterIds: List<Int>) {
        coEvery { repository.getCharactersByIds(characterIds = characterIds) } returns
            QueryResult.Error
    }

    private fun stubRepositoryKillCharacterOnSuccess(character: ModelCharacter) {
        val killedCharacter = character.copy(isKilledByUser = true)
        coEvery { repository.killCharacter(character.id) } returns
            QueryResult.Successful(killedCharacter)
    }

    private fun stubRepositoryKillCharacterOnNoCache(characterId: Int) {
        coEvery { repository.killCharacter(characterId) } returns QueryResult.NoCache
    }

    private fun stubRepositoryKillCharacterOnError(characterId: Int) {
        coEvery { repository.killCharacter(characterId) } returns QueryResult.Error
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        presenter = CharacterListPresenter(repository = repository, statusProvider = statusProvider)
        presenter.bind(view)
    }

    @Test
    fun `loading characters shows loading`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val characters: List<ModelCharacter> = CharacterDataFactory.makeCharacters(count = 5)
        val characterIds: List<Int> = characters.map(ModelCharacter::id)
        stubRepositoryGetCharactersByIdsOnSuccess(
            characterIds = characterIds,
            characters = characters,
        )

        // WHEN
        presenter.loadCharacters(characterIds = characterIds)

        // THEN
        verify(exactly = 1) { view.showLoading() }
    }

    @Test
    fun `loading characters if no network connection shows non-critical offline message`() =
        runBlockingTest {
            // GIVEN
            stubConfigProviderIsOnline(false)
            val characters: List<ModelCharacter> = CharacterDataFactory.makeCharacters(count = 5)
            val characterIds: List<Int> = characters.map(ModelCharacter::id)
            stubRepositoryGetCharactersByIdsOnSuccess(
                characterIds = characterIds,
                characters = characters,
            )

            // WHEN
            presenter.loadCharacters(characterIds = characterIds)

            // THEN
            verify(exactly = 1) { view.showOfflineMessage(isCritical = false) }
        }

    @Test
    fun `loading characters if no network connection hides loading`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(false)
        val characters: List<ModelCharacter> = CharacterDataFactory.makeCharacters(count = 5)
        val characterIds: List<Int> = characters.map(ModelCharacter::id)
        stubRepositoryGetCharactersByIdsOnSuccess(
            characterIds = characterIds,
            characters = characters,
        )

        // WHEN
        presenter.loadCharacters(characterIds = characterIds)

        // THEN
        verify(exactly = 1) { view.hideLoading() }
    }

    @Test
    fun `loading characters successfully fetches correct result`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val characters: List<ModelCharacter> = CharacterDataFactory.makeCharacters(count = 5)
        val characterIds: List<Int> = characters.map(ModelCharacter::id)
        stubRepositoryGetCharactersByIdsOnSuccess(
            characterIds = characterIds,
            characters = characters,
        )

        // WHEN
        presenter.loadCharacters(characterIds = characterIds)

        // THEN
        verify(exactly = 1) { view.setCharacters(characters = characters) }
    }

    @Test
    fun `loading characters successfully hides loading`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val characters: List<ModelCharacter> = CharacterDataFactory.makeCharacters(count = 5)
        val characterIds: List<Int> = characters.map(ModelCharacter::id)
        stubRepositoryGetCharactersByIdsOnSuccess(
            characterIds = characterIds,
            characters = characters
        )

        // WHEN
        presenter.loadCharacters(characterIds = characterIds)

        // THEN
        verify(exactly = 1) { view.hideLoading() }
    }

    @Test
    fun `loading characters on error shows error message`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val characterIds: List<Int> = DataFactory.randomIntList(5)
        stubRepositoryGetCharactersByIdsOnError(characterIds = characterIds)

        // WHEN
        presenter.loadCharacters(characterIds = characterIds)

        // THEN
        verify(exactly = 1) { view.showMessage(any()) }
    }

    @Test
    fun `loading characters on error hides loading`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val characterIds = DataFactory.randomIntList(5)
        stubRepositoryGetCharactersByIdsOnError(characterIds = characterIds)

        // WHEN
        presenter.loadCharacters(characterIds = characterIds)

        // THEN
        verify(exactly = 1) { view.hideLoading() }
    }

    @Test
    fun `loading characters on no cache shows no offline error`() =
        runBlockingTest {
            // GIVEN
            stubConfigProviderIsOnline(true)
            val characterIds = DataFactory.randomIntList(5)
            stubRepositoryGetCharactersByIdsOnNoCache(characterIds = characterIds)

            // WHEN
            presenter.loadCharacters(characterIds = characterIds)

            // THEN
            verify(exactly = 1) { view.onNoOfflineData() }
        }

    @Test
    fun `loading characters on no cache hides loading`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val characterIds = DataFactory.randomIntList(5)
        stubRepositoryGetCharactersByIdsOnNoCache(characterIds = characterIds)

        // WHEN
        presenter.loadCharacters(characterIds = characterIds)

        // THEN
        verify(exactly = 1) { view.hideLoading() }
    }

    @Test
    fun `killing a character successfully fetches correct result`() =
        runBlockingTest {
            // GIVEN
            stubConfigProviderIsOnline(true)
            val character: ModelCharacter = CharacterDataFactory.makeCharacter(
                isAlive = true,
                isKilledByUser = false
            )
            stubRepositoryKillCharacterOnSuccess(character)
            val killedCharacter = character.copy(isKilledByUser = true)

            // WHEN
            presenter.killCharacter(character)

            // THEN
            verify(exactly = 1) { view.updateCharacter(killedCharacter) }
        }

    @Test
    fun `killing a character if no network connection shows non-critical offline message`() =
        runBlockingTest {
            // GIVEN
            stubConfigProviderIsOnline(false)
            val character: ModelCharacter = CharacterDataFactory.makeCharacter(
                isAlive = true,
                isKilledByUser = false
            )
            stubRepositoryKillCharacterOnSuccess(character)

            // WHEN
            presenter.killCharacter(character)

            // THEN
            verify(exactly = 1) { view.showOfflineMessage(isCritical = false) }
        }

    @Test
    fun `killing a character on error shows error message`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val character: ModelCharacter = CharacterDataFactory.makeCharacter(
            isAlive = true,
            isKilledByUser = false
        )
        stubRepositoryKillCharacterOnError(characterId = character.id)

        // WHEN
        presenter.killCharacter(character)

        // THEN
        verify(exactly = 1) { view.showMessage(any()) }
    }

    @Test
    fun `killing a character on no cache shows no offline error`() =
        runBlockingTest {
            // GIVEN
            stubConfigProviderIsOnline(true)
            val character: ModelCharacter = CharacterDataFactory.makeCharacter(
                isAlive = true,
                isKilledByUser = false
            )
            stubRepositoryKillCharacterOnNoCache(characterId = character.id)

            // WHEN
            presenter.killCharacter(character)

            // THEN
            verify(exactly = 1) { view.onNoOfflineData() }
        }
}
