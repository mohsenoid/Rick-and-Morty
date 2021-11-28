package com.mohsenoid.rickandmorty.view.character.list

import com.mohsenoid.rickandmorty.domain.model.ModelCharacter
import com.mohsenoid.rickandmorty.view.base.BasePresenter
import com.mohsenoid.rickandmorty.view.base.BaseView

interface CharacterListContract {

    interface View : BaseView {

        fun onNoOfflineData()

        fun setCharacters(characters: List<ModelCharacter>)

        fun updateCharacter(character: ModelCharacter)
    }

    abstract class Presenter : BasePresenter<View>() {

        abstract suspend fun loadCharacters(characterIds: List<Int>)

        abstract suspend fun killCharacter(character: ModelCharacter)
    }
}
