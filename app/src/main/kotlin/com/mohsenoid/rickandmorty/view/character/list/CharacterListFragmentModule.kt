package com.mohsenoid.rickandmorty.view.character.list

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.mohsenoid.rickandmorty.util.dispatcher.DispatcherProvider
import com.mohsenoid.rickandmorty.view.character.list.adapter.CharacterListAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class CharacterListFragmentModule {

    @Provides
    @Named(CharacterListFragment.ARG_CHARACTER_IDS)
    fun provideCharacterIds(fragment: CharacterListFragment): List<Int>? {
        val args: Bundle = fragment.arguments ?: return null
        return args.getIntegerArrayList(CharacterListFragment.ARG_CHARACTER_IDS)
    }

    @Provides
    fun provideCharacterListFragmentPresenter(
        fragment: CharacterListFragment,
        factory: CharacterListViewModel.Factory
    ): CharacterListViewModel {
        return fragment.activity?.run {
            ViewModelProviders.of(fragment, factory).get(CharacterListViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    @Provides
    fun provideCharacterListAdapterClickListener(fragment: CharacterListFragment): CharacterListAdapter.ClickListener {
        return fragment
    }

    @Provides
    fun provideCharacterListAdapter(
        dispatcherProvider: DispatcherProvider,
        listener: CharacterListAdapter.ClickListener
    ): CharacterListAdapter {
        return CharacterListAdapter(dispatcherProvider = dispatcherProvider, listener = listener)
    }
}
