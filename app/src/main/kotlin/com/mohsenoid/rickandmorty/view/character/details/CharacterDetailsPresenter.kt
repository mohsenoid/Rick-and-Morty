package com.mohsenoid.rickandmorty.view.character.details

import com.mohsenoid.rickandmorty.data.exception.NoOfflineDataException
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.util.config.ConfigProvider

class CharacterDetailsPresenter(
    private val repository: Repository,
    private val configProvider: ConfigProvider
) : CharacterDetailsContract.Presenter() {

    private var view: CharacterDetailsContract.View? = null

    override fun bind(view: CharacterDetailsContract.View) {
        this.view = view
    }

    override fun unbind() {
        view = null
    }

    override suspend fun loadCharacter(characterId: Int) {
        view?.showLoading()
        queryCharacter(characterId)
    }

    private suspend fun queryCharacter(characterId: Int) {
        if (!configProvider.isOnline()) {
            view?.showOfflineMessage(isCritical = false)
        }

        try {
            val result: CharacterEntity = repository.getCharacterDetails(characterId)
            view?.setCharacter(result)
        } catch (e: Exception) {
            if (e is NoOfflineDataException) {
                view?.onNoOfflineData()
            } else {
                view?.showMessage(e.message ?: e.toString())
            }
        } finally {
            view?.hideLoading()
        }
    }
}
