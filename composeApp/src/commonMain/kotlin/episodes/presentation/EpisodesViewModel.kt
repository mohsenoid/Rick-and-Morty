package episodes.presentation

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import episodes.domain.EpisodesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EpisodesViewModel(private val repository: EpisodesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(EpisodesUiState())
    val uiState: StateFlow<EpisodesUiState> by ::_uiState

    fun updateEpisodes() {
        viewModelScope.launch {
            val episodes = repository.getEpisodes(1)
            _uiState.update { currentState ->
                currentState.copy(episodes = episodes)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.close()
    }

    fun onEpisodeClicked(episodeId: Int) {
        TODO("Not yet implemented")
    }
}
