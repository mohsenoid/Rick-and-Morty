package com.mohsenoid.rickandmorty.view.character.details

import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.view.base.BasePresenter
import com.mohsenoid.rickandmorty.view.base.BaseView

interface CharacterDetailsContract {

    interface View : BaseView {

        fun onNoOfflineData()

        fun setCharacter(character: CharacterEntity)
    }

    abstract class Presenter : BasePresenter<View>() {

        abstract suspend fun loadCharacter(characterId: Int)
    }
}
