package com.mohsenoid.rickandmorty.view.character.list

import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.model.ModelCharacter
import com.mohsenoid.rickandmorty.domain.model.QueryResult
import com.mohsenoid.rickandmorty.util.config.ConfigProvider

class CharacterListPresenter(
    private val repository: Repository,
    private val configProvider: ConfigProvider
) : CharacterListContract.Presenter() {

    private var view: CharacterListContract.View? = null

    override fun bind(view: CharacterListContract.View) {
        this.view = view
    }

    override fun unbind() {
        view = null
    }

    override suspend fun loadCharacters(characterIds: List<Int>) {
        view?.showLoading()
        queryCharacters(characterIds)
    }

    private suspend fun queryCharacters(characterIds: List<Int>) {
        if (!configProvider.isOnline()) {
            view?.showOfflineMessage(isCritical = false)
        }

        when (val result = repository.getCharactersByIds(characterIds)) {
            is QueryResult.Successful -> view?.setCharacters(result.data)
            QueryResult.NoCache -> view?.onNoOfflineData()
        }

        view?.hideLoading()
    }

    override suspend fun killCharacter(character: ModelCharacter) {
        if (!character.isAlive) return

        when (val result = repository.killCharacter(character.id)) {
            is QueryResult.Successful -> view?.updateCharacter(result.data)
            QueryResult.NoCache -> view?.onNoOfflineData()
        }
    }
}
