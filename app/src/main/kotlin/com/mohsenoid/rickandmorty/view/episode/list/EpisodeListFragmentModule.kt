package com.mohsenoid.rickandmorty.view.episode.list

import com.mohsenoid.rickandmorty.view.episode.list.adapter.EpisodeListAdapter
import dagger.Module
import dagger.Provides

@Module
class EpisodeListFragmentModule {

    @Provides
    fun provideEpisodeListPresenter(presenter: EpisodeListPresenter): EpisodeListContract.Presenter {
        return presenter
    }

    @Provides
    fun provideEpisodeListAdapterClickListener(fragment: EpisodeListFragment): EpisodeListAdapter.ClickListener {
        return fragment
    }

    @Provides
    fun provideEpisodesListAdapter(listener: EpisodeListAdapter.ClickListener): EpisodeListAdapter {
        return EpisodeListAdapter(listener = listener)
    }
}
