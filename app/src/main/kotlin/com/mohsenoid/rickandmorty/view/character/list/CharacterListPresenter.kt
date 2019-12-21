package com.mohsenoid.rickandmorty.view.character.list

import com.mohsenoid.rickandmorty.data.DataCallback
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

    override fun loadCharacters() {
        view?.showLoading()
        queryCharacters()
    }

    private fun queryCharacters() {
        if (!configProvider.isOnline()) {
            view?.showOfflineMessage(false)
        }
        repository.queryCharactersByIds(characterIds, object : DataCallback<List<CharacterEntity>> {
            override fun onSuccess(result: List<CharacterEntity>) {
                view?.setCharacters(result)
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

    override fun killCharacter(character: CharacterEntity) {
        if (!character.isAlive) return
        repository.killCharacter(character.id, object : DataCallback<CharacterEntity> {
            override fun onSuccess(result: CharacterEntity) {
                view?.updateCharacter(result)
            }

            override fun onError(exception: Exception) {
                view?.showMessage(exception.message ?: exception.toString())
            }
        })
    }
}
