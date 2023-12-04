package com.mohsenoid.rickandmorty.episodes.presentation

import com.mohsenoid.rickandmorty.episodes.domain.EpisodesRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EpisodesViewModel(private val repository: EpisodesRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<EpisodesUiState> = MutableStateFlow(EpisodesUiState.Loading)
    val uiState: StateFlow<EpisodesUiState> by ::_uiState

    fun updateEpisodes() {
        viewModelScope.launch {
            val episodes = repository.getEpisodes(1)
            _uiState.value = EpisodesUiState.Success(episodes)
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.close()
    }
}
