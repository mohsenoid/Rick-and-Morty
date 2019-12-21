package com.mohsenoid.rickandmorty.view.episode.list

import com.mohsenoid.rickandmorty.data.DataCallback
import com.mohsenoid.rickandmorty.data.exception.EndOfListException
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.util.config.ConfigProvider

class EpisodeListPresenter(
    private val repository: Repository,
    private val configProvider: ConfigProvider
) : EpisodeListContract.Presenter {

    private var view: EpisodeListContract.View? = null

    private var page = 1

    override fun bind(view: EpisodeListContract.View) {
        this.view = view
    }

    override fun unbind() {
        view = null
    }

    override fun loadEpisodes() {
        view?.showLoading()
        page = 1
        queryEpisodes()
    }

    override fun loadMoreEpisodes(page: Int) {
        view?.showLoadingMore()
        this.page = page
        queryEpisodes()
    }

    private fun queryEpisodes() {
        if (!configProvider.isOnline()) {
            view?.showOfflineMessage(false)
        }
        repository.queryEpisodes(page, object : DataCallback<List<EpisodeEntity>> {
            override fun onSuccess(result: List<EpisodeEntity>) {
                if (page == 1) {
                    view?.setEpisodes(result)
                    view?.hideLoading()
                } else {
                    view?.updateEpisodes(result)
                    view?.hideLoadingMore()
                }
            }

            override fun onError(exception: Exception) {
                view?.hideLoading()
                view?.hideLoadingMore()
                if (exception is EndOfListException) {
                    view?.reachedEndOfList()
                } else {
                    view?.showMessage(exception.message ?: exception.toString())
                }
            }
        })
    }
}
