package com.mohsenoid.rickandmorty.view.character.details

import android.os.Bundle
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class CharacterDetailsFragmentModule {

    @Provides
    @Named(CharacterDetailsFragment.ARG_CHARACTER_ID)
    fun provideCharacterId(fragment: CharacterDetailsFragment): Int? {
        val args: Bundle = fragment.arguments ?: return null
        return args.getInt(CharacterDetailsFragment.ARG_CHARACTER_ID)
    }

    @Provides
    fun provideCharacterDetailsPresenter(presenter: CharacterDetailsPresenter): CharacterDetailsContract.Presenter {
        return presenter
    }
}
