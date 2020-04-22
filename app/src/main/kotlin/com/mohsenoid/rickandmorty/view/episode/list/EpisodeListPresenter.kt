package com.mohsenoid.rickandmorty.view.episode.list

import com.mohsenoid.rickandmorty.data.exception.EndOfListException
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.util.config.ConfigProvider

class EpisodeListPresenter(
    private val repository: Repository,
    private val configProvider: ConfigProvider
) : EpisodeListContract.Presenter() {

    private var view: EpisodeListContract.View? = null

    private var page = 1

    override fun bind(view: EpisodeListContract.View) {
        this.view = view
    }

    override fun unbind() {
        view = null
    }

    override suspend fun loadEpisodes() {
        view?.showLoading()
        page = 1
        getEpisodes()
    }

    override suspend fun loadMoreEpisodes(page: Int) {
        view?.showLoadingMore()
        this.page = page
        getEpisodes()
    }

    private suspend fun getEpisodes() {
        if (!configProvider.isOnline()) {
            view?.showOfflineMessage(isCritical = false)
        }

        try {
            val result: List<EpisodeEntity> = repository.getEpisodes(page)
            if (page == 1) {
                view?.setEpisodes(result)
            } else {
                view?.updateEpisodes(result)
            }
        } catch (e: Exception) {
            if (e is EndOfListException) {
                view?.reachedEndOfList()
            } else {
                view?.showMessage(e.message ?: e.toString())
            }
        } finally {
            view?.hideLoading()
            view?.hideLoadingMore()
        }
    }
}
