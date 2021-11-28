package com.mohsenoid.rickandmorty.view.episode.list

import com.mohsenoid.rickandmorty.data.exception.EndOfListException
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.model.ModelEpisode
import com.mohsenoid.rickandmorty.test.DataFactory
import com.mohsenoid.rickandmorty.test.EpisodeDataFactory
import com.mohsenoid.rickandmorty.util.StatusProvider
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

class EpisodeListPresenterTest {

    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var statusProvider: StatusProvider

    @Mock
    lateinit var view: EpisodeListContract.View

    private lateinit var presenter: EpisodeListContract.Presenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = EpisodeListPresenter(repository = repository, configProvider = statusProvider)
        presenter.bind(view)
    }

    @Test
    fun `test if loadEpisodes calls view showLoading`() {
        runBlocking {
            // GIVEN

            // WHEN
            presenter.loadEpisodes()

            // THEN
            Verify on view that view.showLoading() was called
        }
    }

    @Test
    fun `test if loadMoreEpisodes calls view showLoadingMore`() {
        runBlocking {
            // GIVEN
            val page = 2

            // WHEN
            presenter.loadMoreEpisodes(page = page)

            // THEN
            Verify on view that view.showLoadingMore() was called
        }
    }

    @Test
    fun `test if isOffline loadEpisodes calls view showOfflineMessage`() {
        runBlocking {
            // GIVEN
            stubConfigProviderIsOnline(false)

            // WHEN
            presenter.loadEpisodes()

            // THEN
            Verify on view that view.showOfflineMessage(isCritical = false) was called
        }
    }

    @Test
    fun `test if isOffline loadMoreEpisodes calls view showOfflineMessage`() {
        runBlocking {
            // GIVEN
            stubConfigProviderIsOnline(false)
            val page = 2

            // WHEN
            presenter.loadMoreEpisodes(page = page)

            // THEN
            Verify on view that view.showOfflineMessage(isCritical = false) was called
        }
    }

    @Test
    fun `test loadEpisodes calls repository getEpisodes`() {
        runBlocking {
            // GIVEN
            val page = 1

            // WHEN
            presenter.loadEpisodes()

            // THEN
            Verify on repository that repository.getEpisodes(page = page) was called
        }
    }

    @Test
    fun `test loadMoreEpisodes calls repository getEpisodes`() {
        runBlocking {
            // GIVEN
            val page = 2

            // WHEN
            presenter.loadMoreEpisodes(page = page)

            // THEN
            Verify on repository that repository.getEpisodes(page = page) was called
        }
    }

    @Test
    fun `test loadEpisodes calls view setEpisodes OnSuccess`() {
        runBlocking {
            // GIVEN
            val episodes: List<ModelEpisode> = EpisodeDataFactory.Entity.makeEpisodes(5)
            stubRepositoryGetEpisodesOnSuccess(episodes)

            // WHEN
            presenter.loadEpisodes()

            // THEN
            Verify on view that view.setEpisodes(episodes = episodes) was called
        }
    }

    @Test
    fun `test loadMoreEpisodes calls view updateEpisodes OnSuccess`() {
        runBlocking {
            // GIVEN
            val page = 2
            val episodes: List<ModelEpisode> = EpisodeDataFactory.Entity.makeEpisodes(5)
            stubRepositoryGetEpisodesOnSuccess(episodes)

            // WHEN
            presenter.loadMoreEpisodes(page = page)

            // THEN
            Verify on view that view.updateEpisodes(episodes = episodes) was called
        }
    }

    @Test
    fun `test loadEpisodes calls view showMessage OnError`() {
        runBlocking {
            // GIVEN
            val errorMessage = DataFactory.randomString()
            stubRepositoryGetEpisodesOnError(Exception(errorMessage))

            // WHEN
            presenter.loadEpisodes()

            // THEN
            Verify on view that view.showMessage(message = errorMessage) was called
        }
    }

    @Test
    fun `test loadMoreEpisodes calls view showMessage OnError`() {
        runBlocking {
            // GIVEN
            val page = 2
            val errorMessage = DataFactory.randomString()
            stubRepositoryGetEpisodesOnError(Exception(errorMessage))

            // WHEN
            presenter.loadMoreEpisodes(page = page)

            // THEN
            Verify on view that view.showMessage(message = errorMessage) was called
        }
    }

    @Test
    fun `test loadEpisodes calls view reachedEndOfList having EndOfListException OnError`() {
        runBlocking {
            // GIVEN
            stubRepositoryGetEpisodesOnError(EndOfListException())

            // WHEN
            presenter.loadEpisodes()

            // THEN
            Verify on view that view.reachedEndOfList() was called
        }
    }

    @Test
    fun `test loadMoreEpisodes calls view reachedEndOfList having EndOfListException OnError`() {
        runBlocking {
            // GIVEN
            val page = 2
            stubRepositoryGetEpisodesOnError(EndOfListException())

            // WHEN
            presenter.loadMoreEpisodes(page = page)

            // THEN
            Verify on view that view.reachedEndOfList() was called
        }
    }

    @Test
    fun `test loadEpisodes calls view hideLoading OnSuccess`() {
        runBlocking {
            // GIVEN
            val episodes: List<ModelEpisode> = EpisodeDataFactory.Entity.makeEpisodes(5)
            stubRepositoryGetEpisodesOnSuccess(episodes)

            // WHEN
            presenter.loadEpisodes()

            // THEN
            Verify on view that view.hideLoading() was called
        }
    }

    @Test
    fun `test loadMoreEpisodes calls view hideLoadingMore OnSuccess`() {
        runBlocking {
            // GIVEN
            val page = 2
            val episodes: List<ModelEpisode> = EpisodeDataFactory.Entity.makeEpisodes(5)
            stubRepositoryGetEpisodesOnSuccess(episodes)

            // WHEN
            presenter.loadMoreEpisodes(page = page)

            // THEN
            Verify on view that view.hideLoadingMore() was called
        }
    }

    @Test
    fun `test loadEpisodes calls view hideLoading OnError`() {
        runBlocking {
            // GIVEN
            stubRepositoryGetEpisodesOnError(Exception())

            // WHEN
            presenter.loadEpisodes()

            // THEN
            Verify on view that view.hideLoading() was called
        }
    }

    @Test
    fun `test loadMoreEpisodes calls view hideLoadingMore OnError`() {
        runBlocking {
            // GIVEN
            val page = 2
            stubRepositoryGetEpisodesOnError(Exception())

            // WHEN
            presenter.loadMoreEpisodes(page = page)

            // THEN
            Verify on view that view.hideLoadingMore() was called
        }
    }

    private fun stubConfigProviderIsOnline(isOnline: Boolean) {
        When calling statusProvider.isOnline() itReturns isOnline
    }

    private suspend fun stubRepositoryGetEpisodesOnSuccess(episodes: List<ModelEpisode>) {
        When calling repository.getEpisodes(any()) itReturns episodes
    }

    private suspend fun stubRepositoryGetEpisodesOnError(exception: Exception) {
        When calling repository.getEpisodes(any()) itAnswers { throw exception }
    }
}
