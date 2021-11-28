package com.mohsenoid.rickandmorty.view.episode.list

import com.mohsenoid.rickandmorty.domain.model.ModelEpisode
import com.mohsenoid.rickandmorty.view.base.BasePresenter
import com.mohsenoid.rickandmorty.view.base.BaseView

interface EpisodeListContract {

    interface View : BaseView {

        fun showLoadingMore()

        fun hideLoadingMore()

        fun setEpisodes(episodes: List<ModelEpisode>)

        fun updateEpisodes(episodes: List<ModelEpisode>)

        fun reachedEndOfList()
    }

    abstract class Presenter : BasePresenter<View>() {

        abstract suspend fun loadEpisodes()

        abstract suspend fun loadMoreEpisodes(page: Int)
    }
}
