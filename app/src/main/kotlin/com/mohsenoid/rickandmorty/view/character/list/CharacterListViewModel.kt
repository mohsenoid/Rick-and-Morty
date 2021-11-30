package com.mohsenoid.rickandmorty.view.character.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.model.ModelCharacter
import com.mohsenoid.rickandmorty.domain.model.QueryResult
import com.mohsenoid.rickandmorty.util.StatusProvider
import com.mohsenoid.rickandmorty.view.mapper.toViewCharacterItem
import com.mohsenoid.rickandmorty.view.model.LoadingState
import com.mohsenoid.rickandmorty.view.model.ViewCharacterItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val characterIds: List<Int>,
    private val repository: Repository,
    private val statusProvider: StatusProvider
) : ViewModel() {

    private val _loadingState: MutableStateFlow<LoadingState> = MutableStateFlow(LoadingState.None)
    val loadingState: StateFlow<LoadingState> = _loadingState

    private val _isOffline: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isOffline: StateFlow<Boolean> = _isOffline

    private val _isNoCache: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isNoCache: StateFlow<Boolean> = _isNoCache

    private val _onError: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val onError: StateFlow<Boolean> = _onError

    private val _characters: MutableStateFlow<MutableList<ViewCharacterItem>> =
        MutableStateFlow(mutableListOf())
    val characters: StateFlow<List<ViewCharacterItem>> = _characters

    private val _selectedCharacterId: Channel<Int> = Channel()
    val selectedCharacterId: Flow<Int> = _selectedCharacterId.receiveAsFlow()

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        _loadingState.value = LoadingState.Loading
        _onError.value = false
        _isNoCache.value = false
        _isOffline.value = false
        queryCharacters()
    }

    private fun queryCharacters() {
        if (!statusProvider.isOnline()) {
            _isOffline.value = true
        }

        viewModelScope.launch {
            when (val result = repository.getCharactersByIds(characterIds)) {
                is QueryResult.Successful -> _characters.value = result.data.toViewCharacterItems()
                QueryResult.NoCache -> _isNoCache.value = true
                QueryResult.Error -> _onError.value = true
            }

            _loadingState.value = LoadingState.None
        }
    }

    private fun killCharacter(character: ModelCharacter) {
        if (!character.isAliveAndNotKilledByUser) return

        if (!statusProvider.isOnline()) {
            _isOffline.value = true
        }

        viewModelScope.launch {
            when (repository.killCharacter(character.id)) {
                is QueryResult.Successful -> {
                    queryCharacters()
                }
                QueryResult.NoCache -> _isNoCache.value = true
                QueryResult.Error -> _onError.value = true
            }
        }
    }

    private fun List<ModelCharacter>.toViewCharacterItems(): MutableList<ViewCharacterItem> =
        map { character ->
            character.toViewCharacterItem(
                onKill = {
                    killCharacter(character)
                },
                onClick = {
                    _selectedCharacterId.trySend(character.id)
                }
            )
        }.toMutableList()
}
