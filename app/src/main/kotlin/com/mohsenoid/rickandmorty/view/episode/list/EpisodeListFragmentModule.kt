package com.mohsenoid.rickandmorty.view.episode.list

import androidx.lifecycle.ViewModelProviders
import com.mohsenoid.rickandmorty.view.episode.list.adapter.EpisodeListAdapter
import dagger.Module
import dagger.Provides

@Module
class EpisodeListFragmentModule {

    @Provides
    fun provideEpisodeListPresenter(
        fragment: EpisodeListFragment,
        factory: EpisodeListViewModel.Factory
    ): EpisodeListViewModel {
        return fragment.activity?.run {
            ViewModelProviders.of(fragment, factory).get(EpisodeListViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
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
