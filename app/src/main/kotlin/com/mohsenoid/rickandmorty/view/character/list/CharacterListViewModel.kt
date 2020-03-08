package com.mohsenoid.rickandmorty.view.character.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mohsenoid.rickandmorty.data.exception.NoOfflineDataException
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.entity.CharacterEntity
import com.mohsenoid.rickandmorty.util.config.ConfigProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterListViewModel @Inject internal constructor(
    private val repository: Repository,
    private val configProvider: ConfigProvider
) : ViewModel() {

    var characterIds: List<Int> = emptyList()

    private val charactersLiveData: MutableLiveData<List<CharacterEntity>> by lazy {
        val liveData = MutableLiveData<List<CharacterEntity>>()
        viewModelScope.launch { queryCharacters() }
        return@lazy liveData
    }

    fun getCharactersLiveData(): LiveData<List<CharacterEntity>> {
        return charactersLiveData
    }

    private val state: MutableLiveData<State> by lazy {
        val liveData = MutableLiveData<State>()
        liveData.postValue(State.Loading)
        return@lazy liveData
    }

    fun getStateLiveData(): LiveData<State> {
        return state
    }

    fun loadCharacters() {
        state.postValue(State.Loading)
        viewModelScope.launch {
            queryCharacters()
        }
    }

    private suspend fun queryCharacters() {
        if (!configProvider.isOnline()) {
            state.postValue(State.Offline)
        }

        try {
            val result: List<CharacterEntity> = repository.getCharactersByIds(characterIds)
            charactersLiveData.postValue(result)
        } catch (e: Exception) {
            if (e is NoOfflineDataException) {
                state.postValue(State.NoOfflineData)
            } else {
                state.postValue(State.Error(e.message ?: e.toString()))
            }
        }
    }

    fun killCharacter(character: CharacterEntity) {
        if (!character.isAlive) return

        try {
            viewModelScope.launch {
                val result: CharacterEntity = repository.killCharacter(character.id)
                charactersLiveData.value?.let { characters ->
                    val index: Int = characters.indexOfFirst { it.id == character.id }
                    val newCharacters: java.util.ArrayList<CharacterEntity> = ArrayList(characters)
                    newCharacters[index] = result
                    charactersLiveData.postValue(newCharacters)
                }
            }
        } catch (e: Exception) {
            state.postValue(State.Error(e.message ?: e.toString()))
        }
    }

    sealed class State {
        object Loading : State()
        object NoOfflineData : State()
        object Offline : State()
        class Error(val errorMessage: String) : State()
    }

    class Factory @Inject internal constructor(
        private val repository: Repository,
        private val configProvider: ConfigProvider
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return CharacterListViewModel(repository, configProvider) as T
        }
    }
}
