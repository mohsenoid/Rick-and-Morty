package com.mohsenoid.rickandmorty.view.character.list

import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.view.base.BasePresenter
import com.mohsenoid.rickandmorty.view.base.BaseView

interface CharacterListContract {

    interface View : BaseView {

        fun onNoOfflineData()

        fun setCharacters(characters: List<CharacterEntity>)

        fun updateCharacter(character: CharacterEntity)
    }

    interface Presenter : BasePresenter<View> {

        var characterIds: List<Int>

        suspend fun loadCharacters()

        suspend fun killCharacter(character: CharacterEntity)
    }
}