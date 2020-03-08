package com.mohsenoid.rickandmorty.view.character.details

import dagger.Module
import dagger.Provides

@Module
class CharacterDetailsFragmentModule {

    @Provides
    fun provideCharacterDetailsPresenter(presenter: CharacterDetailsPresenter): CharacterDetailsContract.Presenter {
        return presenter
    }
}
