package com.mohsenoid.rickandmorty.view.character.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohsenoid.rickandmorty.domain.Repository
import com.mohsenoid.rickandmorty.domain.model.QueryResult
import com.mohsenoid.rickandmorty.util.StatusProvider
import com.mohsenoid.rickandmorty.view.mapper.toViewCharacterDetails
import com.mohsenoid.rickandmorty.view.model.LoadingState
import com.mohsenoid.rickandmorty.view.model.ViewCharacterDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val characterId: Int,
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

    private val _character: MutableStateFlow<ViewCharacterDetails?> = MutableStateFlow(null)
    val character: StateFlow<ViewCharacterDetails?> = _character

    init {
        loadCharacter()
    }

    private fun loadCharacter() {
        _loadingState.value = LoadingState.Loading
        _onError.value = false
        _isNoCache.value = false
        _isOffline.value = false
        queryCharacter()
    }

    private fun queryCharacter() {
        if (!statusProvider.isOnline()) {
            _isOffline.value = true
        }

        viewModelScope.launch {
            when (val result = repository.getCharacterDetails(characterId)) {
                is QueryResult.Successful -> _character.value = result.data.toViewCharacterDetails()
                QueryResult.NoCache -> _isNoCache.value = true
                QueryResult.Error -> _onError.value = true
            }

            _loadingState.value = LoadingState.None
        }
    }
}
