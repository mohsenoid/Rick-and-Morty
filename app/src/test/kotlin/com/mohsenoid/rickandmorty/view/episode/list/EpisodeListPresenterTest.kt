package com.mohsenoid.rickandmorty.view.episode.list

import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.model.ModelEpisode
import com.mohsenoid.rickandmorty.domain.model.PageQueryResult
import com.mohsenoid.rickandmorty.test.EpisodeDataFactory
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
class EpisodeListPresenterTest {

    @MockK
    private lateinit var repository: Repository

    @MockK
    private lateinit var statusProvider: StatusProvider

    private val view: EpisodeListContract.View = mockk {
        every { showLoading() } just runs
        every { showLoadingMore() } just runs
        every { hideLoading() } just runs
        every { hideLoadingMore() } just runs
        every { showMessage(any()) } just runs
        every { showOfflineMessage(any()) } just runs
        every { setEpisodes(any()) } just runs
        every { updateEpisodes(any()) } just runs
        every { reachedEndOfList() } just runs
    }

    private lateinit var presenter: EpisodeListContract.Presenter

    private fun stubConfigProviderIsOnline(isOnline: Boolean) {
        every { statusProvider.isOnline() } returns isOnline
    }

    private fun stubRepositoryGetEpisodesOnSuccess(page: Int, episodes: List<ModelEpisode>) {
        coEvery { repository.getEpisodes(page = page) } returns PageQueryResult.Successful(episodes)
    }

    private fun stubRepositoryGetEpisodesOnEndOfTheList(page: Int) {
        coEvery { repository.getEpisodes(page = page) } returns PageQueryResult.EndOfList
    }

    private fun stubRepositoryGetEpisodesOnError(page: Int) {
        coEvery { repository.getEpisodes(page = page) } returns PageQueryResult.Error
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        presenter = EpisodeListPresenter(repository = repository, statusProvider = statusProvider)
        presenter.bind(view)
    }

    @Test
    fun `loading episodes shows loading`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val episodes: List<ModelEpisode> = EpisodeDataFactory.makeEpisodes(5)
        stubRepositoryGetEpisodesOnSuccess(page = 1, episodes)

        // WHEN
        presenter.loadEpisodes()

        // THEN
        verify(exactly = 1) { view.showLoading() }
    }

    @Test
    fun `loading more episodes shows loading more`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val page = 2
        val episodes: List<ModelEpisode> = EpisodeDataFactory.makeEpisodes(5)
        stubRepositoryGetEpisodesOnSuccess(page = page, episodes = episodes)

        // WHEN
        presenter.loadMoreEpisodes(page = page)

        // THEN
        verify(exactly = 1) { view.showLoadingMore() }
    }

    @Test
    fun `loading episodes if no network connection shows non-critical offline message`() =
        runBlockingTest {
            // GIVEN
            stubConfigProviderIsOnline(false)
            val episodes: List<ModelEpisode> = EpisodeDataFactory.makeEpisodes(5)
            stubRepositoryGetEpisodesOnSuccess(page = 1, episodes)

            // WHEN
            presenter.loadEpisodes()

            // THEN
            verify(exactly = 1) { view.showOfflineMessage(isCritical = false) }
        }

    @Test
    fun `loading more episodes if no network connection shows non-critical offline message`() =
        runBlockingTest {
            // GIVEN
            stubConfigProviderIsOnline(false)
            val page = 2
            val episodes: List<ModelEpisode> = EpisodeDataFactory.makeEpisodes(5)
            stubRepositoryGetEpisodesOnSuccess(page = page, episodes = episodes)

            // WHEN
            presenter.loadMoreEpisodes(page = page)

            // THEN
            verify(exactly = 1) { view.showOfflineMessage(isCritical = false) }
        }

    @Test
    fun `loading episodes successfully fetches correct result`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val episodes: List<ModelEpisode> = EpisodeDataFactory.makeEpisodes(5)
        stubRepositoryGetEpisodesOnSuccess(page = 1, episodes)

        // WHEN
        presenter.loadEpisodes()

        // THEN
        verify(exactly = 1) { view.setEpisodes(episodes = episodes) }
    }

    @Test
    fun `loading episodes successfully hides loading`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val episodes: List<ModelEpisode> = EpisodeDataFactory.makeEpisodes(5)
        stubRepositoryGetEpisodesOnSuccess(page = 1, episodes = episodes)

        // WHEN
        presenter.loadEpisodes()

        // THEN
        verify(exactly = 1) { view.hideLoading() }
    }

    @Test
    fun `loading more episodes successfully fetches correct result`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val page = 2
        val episodes: List<ModelEpisode> = EpisodeDataFactory.makeEpisodes(5)
        stubRepositoryGetEpisodesOnSuccess(page = page, episodes = episodes)

        // WHEN
        presenter.loadMoreEpisodes(page = page)

        // THEN
        verify(exactly = 1) { view.updateEpisodes(episodes = episodes) }
    }

    @Test
    fun `loading more episodes successfully hides loading`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val page = 2
        val episodes: List<ModelEpisode> = EpisodeDataFactory.makeEpisodes(5)
        stubRepositoryGetEpisodesOnSuccess(page = page, episodes = episodes)

        // WHEN
        presenter.loadMoreEpisodes(page = page)

        // THEN
        verify(exactly = 1) { view.hideLoading() }
    }

    @Test
    fun `loading episodes on error shows error message`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        stubRepositoryGetEpisodesOnError(page = 1)

        // WHEN
        presenter.loadEpisodes()

        // THEN
        verify(exactly = 1) { view.showMessage(any()) }
    }

    @Test
    fun `loading episodes on error hides loading`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        stubRepositoryGetEpisodesOnError(page = 1)

        // WHEN
        presenter.loadEpisodes()

        // THEN
        verify(exactly = 1) { view.hideLoading() }
    }

    @Test
    fun `loading more episodes on error shows error message`() = runBlockingTest {
        // GIVEN
        val page = 2
        stubConfigProviderIsOnline(true)
        stubRepositoryGetEpisodesOnError(page = page)

        // WHEN
        presenter.loadMoreEpisodes(page = page)

        // THEN
        verify(exactly = 1) { view.showMessage(any()) }
    }

    @Test
    fun `loading more episodes on error hides loading`() = runBlockingTest {
        // GIVEN
        val page = 2
        stubConfigProviderIsOnline(true)
        stubRepositoryGetEpisodesOnError(page = page)

        // WHEN
        presenter.loadMoreEpisodes(page = page)

        // THEN
        verify(exactly = 1) { view.hideLoading() }
    }

    @Test
    fun `loading episodes on end of list reaches end of list`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        stubRepositoryGetEpisodesOnEndOfTheList(page = 1)

        // WHEN
        presenter.loadEpisodes()

        // THEN
        verify(exactly = 1) { view.reachedEndOfList() }
    }

    @Test
    fun `loading episodes on end of list hides loading`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        stubRepositoryGetEpisodesOnEndOfTheList(page = 1)

        // WHEN
        presenter.loadEpisodes()

        // THEN
        verify(exactly = 1) { view.hideLoading() }
    }

    @Test
    fun `loading more episodes on end of list reaches end of list`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val page = 2
        stubRepositoryGetEpisodesOnEndOfTheList(page = page)

        // WHEN
        presenter.loadMoreEpisodes(page = page)

        // THEN
        verify(exactly = 1) { view.reachedEndOfList() }
    }

    @Test
    fun `loading more episodes on end of list hides loading`() = runBlockingTest {
        // GIVEN
        stubConfigProviderIsOnline(true)
        val page = 2
        stubRepositoryGetEpisodesOnError(page = page)

        // WHEN
        presenter.loadMoreEpisodes(page = page)

        // THEN
        verify(exactly = 1) { view.hideLoading() }
    }
}
