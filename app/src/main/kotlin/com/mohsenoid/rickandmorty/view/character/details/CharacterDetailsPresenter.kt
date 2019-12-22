package com.mohsenoid.rickandmorty.view.character.details

import com.mohsenoid.rickandmorty.data.DataCallback
import com.mohsenoid.rickandmorty.data.exception.NoOfflineDataException
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.util.config.ConfigProvider

class CharacterDetailsPresenter(
    private val repository: Repository,
    private val configProvider: ConfigProvider
) : CharacterDetailsContract.Presenter {

    override var characterId = -1

    private var view: CharacterDetailsContract.View? = null

    override fun bind(view: CharacterDetailsContract.View) {
        this.view = view
    }

    override fun unbind() {
        view = null
    }

    override suspend fun loadCharacter() {
        view?.showLoading()
        queryCharacter(characterId)
    }

    private suspend fun queryCharacter(characterId: Int) {
        if (!configProvider.isOnline()) {
            view?.showOfflineMessage(false)
        }
        repository.queryCharacterDetails(characterId, object : DataCallback<CharacterEntity> {
            override fun onSuccess(result: CharacterEntity) {
                view?.setCharacter(result)
                view?.hideLoading()
            }

            override fun onError(exception: Exception) {
                view?.hideLoading()
                if (exception is NoOfflineDataException) {
                    view?.onNoOfflineData()
                } else {
                    view?.showMessage(exception.message ?: exception.toString())
                }
            }
        })
    }
}
