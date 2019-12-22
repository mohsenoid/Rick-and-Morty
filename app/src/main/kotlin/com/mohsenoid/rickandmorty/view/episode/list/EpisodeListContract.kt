package com.mohsenoid.rickandmorty.view.episode.list

import com.mohsenoid.rickandmorty.domain.entity.EpisodeEntity
import com.mohsenoid.rickandmorty.view.base.BasePresenter
import com.mohsenoid.rickandmorty.view.base.BaseView

interface EpisodeListContract {

    interface View : BaseView {

        fun showLoadingMore()

        fun hideLoadingMore()

        fun setEpisodes(episodes: List<EpisodeEntity>)

        fun updateEpisodes(episodes: List<EpisodeEntity>)

        fun reachedEndOfList()
    }

    interface Presenter : BasePresenter<View> {

        suspend fun loadEpisodes()

        suspend fun loadMoreEpisodes(page: Int)
    }
}
