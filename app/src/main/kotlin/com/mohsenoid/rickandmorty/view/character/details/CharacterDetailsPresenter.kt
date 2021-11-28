package com.mohsenoid.rickandmorty.view.character.details

import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.model.QueryResult
import com.mohsenoid.rickandmorty.util.StatusProvider

class CharacterDetailsPresenter(
    private val repository: Repository,
    private val statusProvider: StatusProvider
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
        if (!statusProvider.isOnline()) {
            view?.showOfflineMessage(isCritical = false)
        }

        when (val result = repository.getCharacterDetails(characterId)) {
            is QueryResult.Successful -> view?.setCharacter(result.data)
            QueryResult.NoCache -> view?.onNoOfflineData()
            QueryResult.Error -> view?.showMessage("Error fetching character details")
        }

        view?.hideLoading()
    }
}
