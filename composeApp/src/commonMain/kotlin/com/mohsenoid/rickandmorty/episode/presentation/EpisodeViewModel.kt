package com.mohsenoid.rickandmorty.episode.presentation

import com.mohsenoid.rickandmorty.episode.domain.EpisodeRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EpisodeViewModel(
    private val episodeId: Int,
    private val repository: EpisodeRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<EpisodeUiState> = MutableStateFlow(EpisodeUiState.Loading)
    val uiState: StateFlow<EpisodeUiState> by ::_uiState

    fun updateEpisode() {
        viewModelScope.launch {
            val episode = repository.getEpisode(episodeId)
            _uiState.value = EpisodeUiState.Success(episode)
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.close()
    }
}
