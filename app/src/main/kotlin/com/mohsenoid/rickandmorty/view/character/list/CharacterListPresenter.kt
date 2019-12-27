package com.mohsenoid.rickandmorty.view.character.list

import com.mohsenoid.rickandmorty.data.exception.NoOfflineDataException
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.util.config.ConfigProvider

class CharacterListPresenter(
    private val repository: Repository,
    private val configProvider: ConfigProvider
) : CharacterListContract.Presenter {

    private var view: CharacterListContract.View? = null

    override var characterIds: List<Int> = emptyList()

    override fun bind(view: CharacterListContract.View) {
        this.view = view
    }

    override fun unbind() {
        view = null
    }

    override suspend fun loadCharacters() {
        view?.showLoading()
        queryCharacters()
    }

    private suspend fun queryCharacters() {
        if (!configProvider.isOnline()) {
            view?.showOfflineMessage(isCritical = false)
        }

        try {
            val result: List<CharacterEntity> = repository.getCharactersByIds(characterIds)
            view?.setCharacters(result)
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

    override suspend fun killCharacter(character: CharacterEntity) {
        if (!character.isAlive) return

        try {
            val result: CharacterEntity = repository.killCharacter(character.id)
            view?.updateCharacter(result)
        } catch (e: Exception) {
            view?.showMessage(e.message ?: e.toString())
        }
    }
}
