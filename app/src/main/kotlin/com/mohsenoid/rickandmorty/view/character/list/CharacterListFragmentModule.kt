package com.mohsenoid.rickandmorty.view.character.list

import com.mohsenoid.rickandmorty.util.dispatcher.DispatcherProvider
import com.mohsenoid.rickandmorty.view.character.list.adapter.CharacterListAdapter
import dagger.Module
import dagger.Provides

@Module
class CharacterListFragmentModule {

    @Provides
    fun provideCharacterListFragmentPresenter(presenter: CharacterListPresenter): CharacterListContract.Presenter {
        return presenter
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
